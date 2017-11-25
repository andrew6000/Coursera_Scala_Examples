package parallel.programming.week.two



object MergeSortWithTask {

  @volatile var dummy: AnyRef = null

  def parMergeSortWithTask(xs: Array[Int], maxDepth: Int): Unit = {
    // 1) Allocate a helper array.
    // This step is a bottleneck, and takes:
    // - ~76x less time than a full quickSort without GCs (best time)
    // - ~46x less time than a full quickSort with GCs (average time)
    // Therefore:
    // - there is a almost no performance gain in executing allocation concurrently to the sort
    // - doing so would immensely complicate the algorithm
    val ys = new Array[Int](xs.length)
    dummy = ys

    // 2) Sort the elements.
    // The merge step has to do some copying, and is the main performance bottleneck of the algorithm.
    // This is due to the final merge call, which is a completely sequential pass.
    def merge(src: Array[Int], dst: Array[Int], from: Int, mid: Int, until: Int) {
      var left = from
      var right = mid
      var i = from
      while (left < mid && right < until) {
        while (left < mid && src(left) <= src(right)) {
          dst(i) = src(left)
          i += 1
          left += 1
        }
        while (right < until && src(right) <= src(left)) {
          dst(i) = src(right)
          i += 1
          right += 1
        }
      }
      while (left < mid) {
        dst(i) = src(left)
        i += 1
        left += 1
      }
      while (right < until) {
        dst(i) = src(right)
        i += 1
        right += 1
      }
    }
    // Without the merge step, the sort phase parallelizes almost linearly.
    // This is because the memory pressure is much lower than during copying in the third step.
    def sort(from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) { // base case
        quickSort(xs, from, until - from)
      } else { // recursively parallelize
        // Divide
        val mid = (from + until) / 2
        parallel(sort(mid, until, depth + 1), sort(from, mid, depth + 1))
        // Merge two sorted sublists
        val flip = (maxDepth - depth) % 2 == 0
        val src = if (flip) ys else xs
        val dst = if (flip) xs else ys
        merge(src, dst, from, mid, until)
      }
    }

    sort(0, xs.length, 0)

    // 3) In parallel, copy the elements back into the source array.
    // Executed sequentially, this step takes:
    // - ~23x less time than a full quickSort without GCs (best time)
    // - ~16x less time than a full quickSort with GCs (average time)
    // There is a small potential gain in parallelizing copying.
    // However, most Intel processors have a dual-channel memory controller,
    // so parallel copying has very small performance benefits.
    def copy(src: Array[Int], target: Array[Int], from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) {
        Array.copy(src, from, target, from, until - from)
      } else {
        val mid = from + ((until - from) / 2)
        val right = task {
          copy(src, target, mid, until, depth + 1)
        }
        copy(src, target, from, mid, depth + 1)
        right.join()
      }
    }
    if (maxDepth % 2 != 0) {
      copy(ys, xs, 0, xs.length, 0)
    }
  }

}
