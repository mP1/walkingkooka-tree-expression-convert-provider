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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.provider.ConverterInfo;
import walkingkooka.convert.provider.ConverterInfoSet;
import walkingkooka.convert.provider.ConverterName;
import walkingkooka.convert.provider.ConverterProvider;
import walkingkooka.convert.provider.ConverterSelector;
import walkingkooka.net.UrlPath;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.ExpressionNumberConverters;

import java.util.List;
import java.util.Objects;

/**
 * A {@link ConverterProvider} for the {@link Converter converter(s)} in {@link walkingkooka.tree.expression.ExpressionNumberConverters}.
 */
final class TreeExpressionConvertersConverterProvider implements ConverterProvider {

    /**
     * Singleton
     */
    final static TreeExpressionConvertersConverterProvider INSTANCE = new TreeExpressionConvertersConverterProvider();

    private TreeExpressionConvertersConverterProvider() {
        super();
    }

    @Override
    public <C extends ConverterContext> Converter<C> converter(final ConverterSelector selector,
                                                               final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");

        return selector.evaluateValueText(
            this,
            context
        );
    }

    @Override
    public <C extends ConverterContext> Converter<C> converter(final ConverterName name,
                                                               final List<?> values,
                                                               final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        Converter<?> converter;

        final List<?> copy = Lists.immutable(values);
        final int count = copy.size();

        switch (name.value()) {
            case NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 values got " + count + " " + values);
                }
                converter = ExpressionNumberConverters.numberOrExpressionNumberToNumber();
                break;
            case NUMBER_TO_NUMBER_STRING:
                if (0 != count) {
                    throw new IllegalArgumentException("Expected 0 values got " + count + " " + values);
                }
                converter = ExpressionNumberConverters.numberToNumber();
                break;
            case TO_EXPRESSION_NUMBER_THEN_STRING:
                if (2 != count) {
                    throw new IllegalArgumentException("Expected 2 values got " + count + " " + values);
                }
                converter = ExpressionNumberConverters.toExpressionNumberThen(
                    getConverterFromValues(copy, 0),
                    getConverterFromValues(copy, 1)
                );
                break;
            case TO_NUMBER_OR_EXPRESSION_NUMBER_STRING:
                if (1 != count) {
                    throw new IllegalArgumentException("Expected 1 values got " + count + " " + values);
                }
                converter = ExpressionNumberConverters.toNumberOrExpressionNumber(
                    getConverterFromValues(copy, 0)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown converter " + name);
        }

        return Cast.to(converter);
    }

    private <C extends ConverterContext> Converter<C> getConverterFromValues(final List<?> values,
                                                                             final int i) {
        final Object value = values.get(i);
        if (false == value instanceof Converter) {
            throw new IllegalArgumentException("Expected converter in value " + i + " but got " + CharSequences.quoteIfChars(value));
        }

        return Cast.to(value);
    }

    private final static String NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER_STRING = "number-or-expression-number-to-number";

    final static ConverterName NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER = ConverterName.with(NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER_STRING);

    private final static String NUMBER_TO_NUMBER_STRING = "number-to-number";

    final static ConverterName NUMBER_TO_NUMBER = ConverterName.with(NUMBER_TO_NUMBER_STRING);

    private final static String TO_EXPRESSION_NUMBER_THEN_STRING = "to-expression-number-then";

    final static ConverterName TO_EXPRESSION_NUMBER_THEN = ConverterName.with(TO_EXPRESSION_NUMBER_THEN_STRING);

    private final static String TO_NUMBER_OR_EXPRESSION_NUMBER_STRING = "to-number-or-expression-number";

    final static ConverterName TO_NUMBER_OR_EXPRESSION_NUMBER = ConverterName.with(TO_NUMBER_OR_EXPRESSION_NUMBER_STRING);

    /**
     * Helper that creates a {@link ConverterInfo} from the given {@link ConverterName} and {@link TreeExpressionConvertProviders#BASE_URL}.
     */
    private static ConverterInfo converterInfo(final ConverterName name) {
        return ConverterInfo.with(
            TreeExpressionConvertProviders.BASE_URL.appendPath(
                UrlPath.parse(
                    name.value()
                )
            ),
            name
        );
    }

    @Override
    public ConverterInfoSet converterInfos() {
        return INFOS;
    }

    private final static ConverterInfoSet INFOS = ConverterInfoSet.with(
        Sets.of(
            converterInfo(NUMBER_OR_EXPRESSION_NUMBER_TO_NUMBER),
            converterInfo(NUMBER_TO_NUMBER),
            converterInfo(TO_EXPRESSION_NUMBER_THEN),
            converterInfo(TO_NUMBER_OR_EXPRESSION_NUMBER)
        )
    );

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
