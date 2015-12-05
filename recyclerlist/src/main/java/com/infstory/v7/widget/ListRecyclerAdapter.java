/*
 * Copyright (C) 2015 8tory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.infstory.v7.widget;

import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListRecyclerAdapter<T, VH extends Presenter<T>> extends RecyclerView.Adapter<VH> {
    private List<T> mList = Collections.emptyList();
    protected Action3<VH, Integer, T> mOnBindViewHolder;
    protected Func2<ViewGroup, Integer, VH> mPresenter;

    public interface Func2<T1, T2, R> {
        R call(T1 t1, T2 t2);
    }

    public interface Action3<T1, T2, T3> {
        void call(T1 t1, T2 t2, T3 t3);
    }

    public ListRecyclerAdapter(List<T> list) {
        mList = list;
    }

    public static <R, VHH extends Presenter<R>> ListRecyclerAdapter<R, VHH> create() {
        return create(new ArrayList<>());
    }

    public static <R, VHH extends Presenter<R>> ListRecyclerAdapter<R, VHH> create(List<R> list) {
        return new ListRecyclerAdapter<>(list);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPresenter != null) return mPresenter.call(parent, viewType);
        return null;
    }

    public ListRecyclerAdapter<T, VH> createPresenter(Func2<ViewGroup, Integer, VH> presenter) {
        mPresenter = presenter;
        return this;
    }

    private boolean onBindViewHolderSupered;

    @Override
    public void onBindViewHolder(VH viewHolder, int position) { // final, DO NOT Override until certainly
        onBindViewHolderSupered = false;
        onBindViewHolder(viewHolder, position, mList.get(position));
        if (!onBindViewHolderSupered) throw new IllegalArgumentException("super.onBindViewHolder() not be called");
    }

    // super me
    public void onBindViewHolder(VH viewHolder, int position, T item) { // final, DO NOT Override until certainly
        onBindViewHolderSupered = true;
        if (mOnBindViewHolder == null) {
            mOnBindViewHolder = (v, p, i) -> v.onBind(p, i);
        }
        mOnBindViewHolder.call(viewHolder, position, item);
    }

    public ListRecyclerAdapter<T, VH> bindViewHolder(Action3<VH, Integer, T> onBindViewHolder) {
        mOnBindViewHolder = onBindViewHolder;
        return this;
    }

    @Override
    public int getItemCount() {
        int i = mList.size();
        return i;
    }

    public List<T> getList() {
        return mList;
    }
}
