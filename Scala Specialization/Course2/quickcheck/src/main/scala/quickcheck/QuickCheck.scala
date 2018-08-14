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
    val h = meld(insert(a1, empty), insert(a2, empty))
    val min = findMin(h)
    val max = findMin(deleteMin(h))

    ord.lteq(min, max)
  }

  property("meld of heap and empty heap product original heap") = forAll { (h: H) =>
    meld(h, empty) == h && meld(empty, h) == h
  }

//  property("meld ")


//    property("deleteMin") = forAll { (h: List[Int]) =>
//
//    }

  property("meld of 2 heaps contains elements of both heaps") = forAll { (a1: A, a2: A) =>
    val h = meld(insert(a1, empty), insert(a2, empty))
    val min = findMin(h)
    val max = findMin(deleteMin(h))

    min == a1 && max == a2 || min == a2 && max == a1
   }

  property("deleteMin on 1 element heap") = forAll { (a: A) =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }

  property("deleteMin on 2 element heap") = forAll { (a1: A, a2: A) =>
    val h = meld(insert(a1, empty), insert(a2, empty))
    deleteMin(deleteMin(h)) == empty
  }

//  property("") = forAll { (h: List[Int]) =>
//    insert(h.head, empty)
//  }

  property("heap is sorted as min is continuously deleted") = forAll { (h: H) =>

    def validHeap(h: H, cache: List[A]): Boolean = {
      if(isEmpty(h)) return true

      val min = findMin(h)

      if(cache.forall(ord.gteq(min, _))) validHeap(deleteMin(h), min :: cache.reverse)
      else false
    }

    validHeap(h, List())
  }

  property("melding two heaps contains all elements in sorted order") = forAll { (h1: H, h2: H) =>

    def getHeapElements(h: H, cache: List[A]): List[A] = {
      if(isEmpty(h)) return cache

      val min = findMin(h)

      getHeapElements(deleteMin(h), min :: cache)
    }

    val meldedElements = getHeapElements(meld(h1, h2), List())
    val h1Elements = getHeapElements(h1, List())
    val h2Elements = getHeapElements(h2, List())

    !meldedElements.filter(element => !h1Elements.contains(element)).exists(element => !h2Elements.contains(element))
  }

//  property("min of two melded heaps produces min of one or the other") = forAll { (h1: H, h2: H) =>
////    val heap1 = insert(a1, empty)
////    val heap2 = insert(a2, empty)
//    val h = meld(h1, h2)
//
//    val min = findMin(h)
//    val max = findMin(deleteMin(h))
//
//    min == findMin(h1) || min == findMin(h2)
//  }
}
