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

import org.junit.jupiter.api.Test;
import walkingkooka.convert.provider.ConverterProviderTesting;
import walkingkooka.convert.provider.ConverterSelector;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.tree.expression.ExpressionNumberConverters;

public final class TreeExpressionConvertersConverterProviderTest implements ConverterProviderTesting<TreeExpressionConvertersConverterProvider> {

    @Test
    public void testNumberOrExpressionNumberToNumber() {
        this.converterAndCheck(
                TreeExpressionConvertersConverterProvider.NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER + "",
                ExpressionNumberConverters.numberOrExpressionNumberToNumber()
        );
    }

    @Test
    public void testToNumberOrExpressionNumber() {
        this.converterAndCheck(
                TreeExpressionConvertersConverterProvider.TO_NUMBER_OR_EXPRESSION_NUMBER + " (" + TreeExpressionConvertersConverterProvider.NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER + ")",
                ExpressionNumberConverters.toNumberOrExpressionNumber(
                        ExpressionNumberConverters.numberOrExpressionNumberToNumber()
                )
        );
    }

    @Test
    public void testToExpressionNumberThen() {
        this.converterAndCheck(
                TreeExpressionConvertersConverterProvider.TO_EXPRESSION_NUMBER_THEN + " (" + TreeExpressionConvertersConverterProvider.NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER + "," + TreeExpressionConvertersConverterProvider.NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER + ")",
                ExpressionNumberConverters.toExpressionNumberThen(
                        ExpressionNumberConverters.numberOrExpressionNumberToNumber(),
                        ExpressionNumberConverters.numberOrExpressionNumberToNumber()
                )
        );
    }

    @Override
    public TreeExpressionConvertersConverterProvider createConverterProvider() {
        return TreeExpressionConvertersConverterProvider.INSTANCE;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<TreeExpressionConvertersConverterProvider> type() {
        return TreeExpressionConvertersConverterProvider.class;
    }
}
