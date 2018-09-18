package com.thn.lexi.order

import com.thn.lexi.beans.ProductBean

class SortBy:Comparator<ProductBean>{
    override fun compare(o1: ProductBean, o2: ProductBean): Int {
        return o1.fid.compareTo(o2.fid)
    }
}