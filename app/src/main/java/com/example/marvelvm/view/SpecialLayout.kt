package com.example.marvelvm.view

import android.content.Context
import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max
import kotlin.math.min

class SpecialLayout(context: Context?) : LinearLayoutManager(context) {
    private val viewCache = SparseArray<View?>()
    private var topView = 0
    private var bottomView = 0
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        //   detachAndScrapAttachedViews(recycler);
        if (itemCount > 0) {
            fill(recycler)
        }
    }

    /*  viewBottom   -   cмещение от верха экрана первого View в RecyclerView
           anchorPos     -  номер картинки  в адаптере с  которой начинаем заполнять наш лэйоут
                            самая первая картинка равеа нулю    */
    private fun fillUp(recycler: Recycler, anchorView: View?): Int {
        val itemSize = width / 3 //Размер одного маленького элемента лейоута
        val widthSpec = View.MeasureSpec.makeMeasureSpec(itemSize, View.MeasureSpec.EXACTLY)
        val widthSpec2 = View.MeasureSpec.makeMeasureSpec(itemSize * 2, View.MeasureSpec.EXACTLY)
        var pos: Int
        var item = 0
        var viewTopRow = 0
        if (anchorView != null) {
            item = getPosition(anchorView)
            viewTopRow = getDecoratedTop(anchorView)
        }

        //Определяем позицию картинки в нашем лэйоуте
        pos = if (item > 11) {
            item - item / 12 * 12
        } else {
            item
        }

        //Начинаем заполнять Recycle с низу вверх (с позиции предшнствующей ancher)
        item--
        pos--
        if ((pos < 0) and (anchorView != null)) {
            pos = 11
        }
        var viewTop = 0 //Вернет при вызове из onLayoutChildren так как ничего заполнятся не будет
        while (pos >= 0) {
            if (pos >= 11) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow - itemSize, itemSize * 3, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 10) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize,
                    viewTopRow - itemSize, itemSize * 2, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 9) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, 0,
                    viewTopRow - itemSize, itemSize, viewTopRow
                )
                if (item <= 0 || viewTop < 0) {
                    break
                } else {
                    viewTopRow -= itemSize
                    item--
                }
            }
            if (pos >= 8) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, 0,
                    viewTopRow - itemSize, itemSize, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 7) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec2, itemSize,
                    viewTopRow - itemSize * 2, itemSize * 3, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 6) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, 0,
                    viewTopRow - itemSize * 2, itemSize, viewTopRow - itemSize
                )
                if (item <= 0 || viewTop < 0) {
                    break
                } else {
                    viewTopRow -= itemSize * 2
                    item--
                }
            }
            if (pos >= 5) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow - itemSize, itemSize * 3, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 4) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize,
                    viewTopRow - itemSize, itemSize * 2, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 3) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, 0,
                    viewTopRow - itemSize, itemSize, viewTopRow
                )
                if (item <= 0 || viewTop < 0) {
                    break
                } else {
                    viewTopRow -= itemSize
                    item--
                }
            }
            if (pos >= 2) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow - itemSize, itemSize * 3, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
            if (pos >= 1) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec2, 0,
                    viewTopRow - itemSize * 2, itemSize * 2, viewTopRow
                )
                if (item <= 0) {
                    break
                } else {
                    item--
                }
            }
  //          if (pos >= 0) {
                viewTop = setViewParametersUp(
                    recycler, item, widthSpec, itemSize * 2, viewTopRow - itemSize * 2,
                    itemSize * 3, viewTopRow - itemSize
                )
                if (item <= 0 || viewTop < 0) {
                    break
                } else {
                    viewTopRow -= itemSize * 2
                    pos = 11
                    item--
                }
   //         }
        }
        return viewTop //Возвращает ViewTop = 0 при первом проходе (из onLayotChildren)
    }

    /*  viewBottom   -   cмещение от верха экрана до низа View в RecyclerView
        anchorPos     -  номер картинки  в адаптере с  которой начинаем заполнять наш лэйоут
                         (самая первая картинка равеа нулю)
                         */
    private fun fillDown(recycler: Recycler, anchorView: View?): Int {
        val itemSize = width / 3 //Размер одного маленького элемента
        val widthSpec = View.MeasureSpec.makeMeasureSpec(itemSize, View.MeasureSpec.EXACTLY)
        val widthSpec2 = View.MeasureSpec.makeMeasureSpec(itemSize * 2, View.MeasureSpec.EXACTLY)
        val itemCount = itemCount //Всего элементов в адаптере
        val height = height //Высота экрана
        var pos: Int
        var viewBottom: Int
        var viewTopRow = 0
        var item = 0
        if (anchorView != null) {
            item = getPosition(anchorView) //Определяет позицию ancherView среди всех эл-ов одаптера
            viewTopRow =
                getDecoratedTop(anchorView) //Вертикальная координата заполнемой строки Recycle
        }
        pos = if (item > 11) {
            item - item / 12 * 12
        } else {
            item
        }
        while (true) {
            if (pos <= 0) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize * 2, viewTopRow,
                    itemSize * 3, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 1) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec2, 0,
                    viewTopRow, itemSize * 2, viewTopRow + itemSize * 2
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 2) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow + itemSize, itemSize * 3, viewTopRow + itemSize * 2
                )
                if (viewBottom > height || item >= itemCount - 1) {
                    break
                } else {
                    viewTopRow = viewBottom
                    item++
                }
            }
            if (pos <= 3) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, 0,
                    viewTopRow, itemSize, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 4) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize,
                    viewTopRow, itemSize * 2, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 5) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow, itemSize * 3, viewTopRow + itemSize
                )
                if (viewBottom > height || item >= itemCount - 1) {
                    break
                } else {
                    viewTopRow = viewBottom
                    item++
                }
            }
            if (pos <= 6) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, 0,
                    viewTopRow, itemSize, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 7) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec2, itemSize,
                    viewTopRow, itemSize * 3, viewTopRow + itemSize * 2
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 8) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, 0,
                    viewTopRow + itemSize, itemSize, viewTopRow + itemSize * 2
                )
                if (viewBottom > height || item >= itemCount - 1) {
                    break
                } else {
                    viewTopRow = viewBottom
                    item++
                }
            }
            if (pos <= 9) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, 0,
                    viewTopRow, itemSize, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 10) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize,
                    viewTopRow, itemSize * 2, viewTopRow + itemSize
                )
                if (item >= itemCount - 1) {
                    break
                } else {
                    item++
                }
            }
            if (pos <= 11) {
                viewBottom = setViewParametersDown(
                    recycler, item, widthSpec, itemSize * 2,
                    viewTopRow, itemSize * 3, viewTopRow + itemSize
                )
                if (viewBottom > height || item >= itemCount - 1) {
                    break
                } else {
                    viewTopRow = viewBottom
                    item++
                    pos = 0
                }
            }
        }
        return viewBottom
    }

    private fun fill(recycler: Recycler) {
        var anchorView: View?
        var ancher: Int
        viewCache.clear()

        // Помещаем вьюшки из лэйоута в кэш и...
        run {
            var i = 0
            val cnt = childCount
            while (i < cnt) {
                val view =
                    getChildAt(i) // Возвращает view из лэйоута по индексу
                val pos = getPosition(view!!) // Возвращает позицию View в адаптере
                viewCache.put(pos, view)
                i++
            }
        }

        // Определяем якорную вьюшку
        // Это будет первая View в последней строке лэйоута, которая полностью видна
        // Используем cash, потому что там номера вьюшек отсортированы по возрастанию
        // в ресайклвью они идут не попорядку (список сотоит из двух частей), поэтому
        // его использовать нельзя: при движении списка вверх идет белеберда
        val cashCount = viewCache.size() //Количество вьюшек в ресайклере
        if (cashCount > 3) {
            ancher = viewCache.keyAt(cashCount - 1) // Номер в адаптере последней вью
            while (ancher - ancher / 3 * 3 != 0) {    // Если это не первая вью в строке, то
                ancher-- // уменьшаем ее номер, пока не достигнеи
            } // начала строки
            anchorView = viewCache[ancher]
            if (getDecoratedTop(anchorView!!) > height) { // Проверяем не вылазет ли низ якорной
                ancher -= 3 // вью за границы экрана снизу, если
            } // вылазит, то берем якорь со строки выше
        } else {
            ancher = 0
        }
        anchorView = viewCache[ancher]

        //... и удалям из лэйаута (очищаем его)
        for (i in 0 until viewCache.size()) {
            detachView(viewCache.valueAt(i)!!)
        }
        topView = fillUp(recycler, anchorView) //Заполняем лэйоут от якоря вверх
        bottomView = fillDown(recycler, anchorView) //Заполняем лэйоут от якоря вниз

        //Рециркулирукм лэйоут
        for (i in 0 until viewCache.size()) {
            recycler.recycleView(viewCache.valueAt(i)!!)
        }
    }

    private fun setViewParametersDown(
        recycler: Recycler, item: Int, itemSpecSize: Int,
        left: Int, top: Int, right: Int, bottom: Int
    ): Int {
        var view = viewCache[item]
        if (view == null) {
            view = recycler.getViewForPosition(item)
            addView(view)
            measureChildWithDecorationsAndMargin(view, itemSpecSize, itemSpecSize)
            layoutDecorated(view, left, top, right, bottom)
        } else {
            attachView(view)
            viewCache.remove(item)
        }
        return getDecoratedBottom(view)
    }

    private fun setViewParametersUp(
        recycler: Recycler, item: Int, itemSpecSize: Int,
        left: Int, top: Int, right: Int, bottom: Int
    ): Int {
        var view = viewCache[item]
        if (view == null) {
            view = recycler.getViewForPosition(item)
            addView(view, 0)
            measureChildWithDecorationsAndMargin(view, itemSpecSize, itemSpecSize)
            layoutDecorated(view, left, top, right, bottom)
        } else {
            attachView(view)
            viewCache.remove(item)
        }
        return getDecoratedTop(view)
    }

    private fun measureChildWithDecorationsAndMargin(child: View, _widthSpec: Int, _heightSpec: Int) {
        var widthSp = _widthSpec
        var heightSp = _heightSpec
        val decorRect = Rect()
        calculateItemDecorationsForChild(child, decorRect)
        val lp = child.layoutParams as RecyclerView.LayoutParams
        widthSp = updateSpecWithExtra(
            widthSp, lp.leftMargin + decorRect.left,
            lp.rightMargin + decorRect.right
        )
        heightSp = updateSpecWithExtra(
            heightSp, lp.topMargin + decorRect.top,
            lp.bottomMargin + decorRect.bottom
        )
        child.measure(widthSp, heightSp)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        //Декарации и отступа нет, поэтомуво звращается собственный размер
        if (startInset == 0 && endInset == 0) {
            return spec
        }
        val mode = View.MeasureSpec.getMode(spec)
        //Вганяет в границы с учетом декарации и отступа
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset, mode
            )
        } else spec
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val delta = scrollVerticallyInternal(dy)
        offsetChildrenVertical(-delta)
        fill(recycler)
        return delta
    }

    //  Функция определяет на сколько нужно сдвигать экран:
    //  1. Если элементов в адаптере больше, чем прорисовано в RecyclerView, то сдвигаем на сколько
    //  попросили: dy
    //  2. Если все элементы адаптера прорисованы в RecyclerView, то сдвигаем только до последнего
    //  элемента
    private fun scrollVerticallyInternal(dy: Int): Int {
        val childCount = childCount
        val itemCount = itemCount
        if (childCount == 0) {
            return 0
        }

        //Случай, когда все вьюшки поместились на экране
        val viewSpan = bottomView - topView
        if (viewSpan <= height) {
            return 0
        }
        var delta = 0
        //если контент уезжает вниз,
        //а он может уезжать вниз только если заполнен весь лейоут
        if (dy < 0) {
            val ancherView = getChildAt(childCount - 4) //Находим якорь
            //Первая позиция адаптере в лейоуте ниже якоря никогда на спустится, поэтому:
            val firstViewAdapterPos = getPosition(ancherView!!)
            delta = if (firstViewAdapterPos > 0) { //если это не первая позиция в адаптере,
                dy //то сдвигаем на сколько просят
            } else { //если  же это первая вьюшка в адаптере и выше их больше быть не может
                max(topView, dy)
            }
        } else if (dy > 0) { //если контент уезжает вверх
            val lastView = getChildAt(childCount - 1)
            val lastViewAdapterPos =
                getPosition(lastView!!) //Возвращает номер по следней позиции в адаптере
            delta =
                if (lastViewAdapterPos < itemCount - 1) { //если нижняя вюшка не самая последняя в адаптере
                    dy
                } else { //если нижняя вьюшка самая последняя в адаптере и ниже вьюшек больше быть не может
                    val viewBottom = getDecoratedBottom(lastView)
                    val parentBottom = height
                    min(viewBottom - parentBottom, dy)
                }
        }
        return delta
    }
}