package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = {
    for(
      n <- arbitrary[A];
      heap <- oneOf(const(empty), genHeap)
    ) yield meld(insert(n, empty), heap)
  }

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H, a: A) =>
    val m = if (isEmpty(h)) a else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min finds min of 1 element heap") = forAll { (a: A) =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min finds min on 2 element heap") = forAll { (a1: A, a2: A) =>
    val h = insert(a2, insert(a1, empty))
    val min = findMin(h)
    val max = findMin(deleteMin(h))

    min <= max
  }

  property("deleteMin on 1 element heap") = forAll { (a: A) =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }

  property("heap is sorted as min is continuously deleted") = forAll { (h: H) =>
//    def isSorted(h: H, cache: List[A]): Boolean = {
//      if(isEmpty(h)) return cache.sorted == cache
//
//      val min = findMin(h)
//
//      validHeap(deleteMin(h), min :: cache)
//    }

    def validHeap(h: H, cache: List[A]): Boolean = {
      if(isEmpty(h)) return true

      val min = findMin(h)



      if(cache.forall(ord.lteq(_, min))) validHeap(deleteMin(h), min :: cache)
      else false
    }

    validHeap(h, List[A]())
  }

  property("min of two melded heaps produces min of one or the other") = forAll { (h1: H, h2: H) =>
//    val heap1 = insert(a1, empty)
//    val heap2 = insert(a2, empty)
    val h = meld(h1, h2)

    val min = findMin(h)

    min == findMin(h1) || min == findMin(h2)
  }
}
