# RecyclerList

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerList-green.svg?style=flat)](https://android-arsenal.com/details/1/2151)
[![Download](https://api.bintray.com/packages/yongjhih/maven/recyclerlist/images/download.svg) ](https://bintray.com/yongjhih/maven/recyclerlist/_latestVersion)
[![JitPack](https://img.shields.io/github/tag/yongjhih/recyclerlist.svg?label=JitPack)](https://jitpack.io/#yongjhih/recyclerlist)
[![Build Status](https://travis-ci.org/yongjhih/RecyclerList.svg)](https://travis-ci.org/yongjhih/RecyclerList)
[![Join the chat at https://gitter.im/yongjhih/recyclerlist](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/yongjhih/recyclerlist?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

![](art/recyclerlist.png)

Easy to use RecyclerView.

## Usage

Before:

```java
@InjectView(R.id.icons)
RecyclerView icons;

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.inject(this);

    final List<String> list = new ArrayList<>(Arrays.asList("http://example.com/a.png")));

    RecyclerAdapter<IconViewHolder> listAdapter = new RecyclerAdapter<>() {
        @Override
        public IconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new IconViewHolder(LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false)));
        }

        @Override
        public void onBindViewHolder(IconViewHolder viewHolder, int position) {
            viewHolder.icon.setImageURI(Uri.parse(list.get(position)));
        }
    }

    icons.setLayoutManager(new LinearLayoutManager(activity));
    icons.setAdapter(listAdapter);

    list.add("http://example.com/b.png");
    listAdapter.notifyDataSetChanged();
}
```

```java
public class IconViewHolder extends BindViewHolder<String> {
    @InjectView(R.id.icon)
    public SimpleDraweeView icon;

    public IconViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
```

After:

```java
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.inject(this);

    ListRecyclerAdapter<String, IconPresenter> listAdapter = ListRecyclerAdapter.create();
    listAdapter.getList().add("http://example.com/a.png");

    listAdapter.createPresenter((parent, viewType) -> new IconPresenter(LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false)));

    icons.setLayoutManager(new LinearLayoutManager(activity));
    icons.setAdapter(listAdapter);

    listAdapter.getList().add("http://example.com/b.png");
    listAdapter.notifyDataSetChanged(); // TODO hook List.add(), List.addAll(), etc. modifitable operations
}
```

```java
public class IconPresenter extends Presenter<String> {
    @InjectView(R.id.icon)
    public SimpleDraweeView icon;

    public IconViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void onBind(int position, String item) {
        icon.setImageURI(IconViewHolder(Uri.parse(item)));
    }
}
```

## Installation

via jcenter:

```gradle
repositories {
    jcenter()
}

dependencies {
    compile 'com.infstory:recyclerlist:1.0.0'
}
```

via jitpack.io:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.yongjhih:recyclerlist:-SNAPSHOT'
}
```

## See Also

* https://gist.github.com/yongjhih/d8db8c69190293098eec

## License

```
Copyright 2015 8tory, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
