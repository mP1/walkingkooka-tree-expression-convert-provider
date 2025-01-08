/*
 * Copyright 2024 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.expression.convert.provider;

import walkingkooka.convert.Converter;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.reflect.PublicStaticHelper;

public final class TreeExpressionConvertProviders implements PublicStaticHelper {
    /**
     * This is the base {@link AbsoluteUrl} for all {@link Converter} in this package. The name of each
     * converter will be appended to this base.
     */
    public final static AbsoluteUrl BASE_URL = Url.parseAbsolute(
        "https://github.com/mP1/walkingkooka-tree/" + Converter.class.getSimpleName()
    );

    /**
     * {see TreeExpressionConvertersConverterProvider}
     */
    public static ConverterProvider treeExpressionConvertersConverterProvider() {
        return TreeExpressionConvertersConverterProvider.INSTANCE;
    }

    /**
     * Stop creation
     */
    private TreeExpressionConvertProviders() {
        throw new UnsupportedOperationException();
    }
}
