/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boredream.designrescollection.base;

public interface BaseView<T> {

    void setPresenter(T presenter);

    /**
     * 界面是否活动的
     *
     * @return
     */
    boolean isActive();

    /**
     * 显示交互等待提示框
     */
    void showProgress();

    /**
     * 取消显示交互等待提示框
     */
    void dismissProgress();

    void showTip(String message);

}
