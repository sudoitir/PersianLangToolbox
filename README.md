# Persian Lang Toolbox

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange)](https://www.java.com)
[![Dependency](https://img.shields.io/badge/Dependency-None-brightgreen)](#)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)
<a href=" https://github.com/sudoitir/PersianLangToolbox/actions/workflows/maven.yml">
    <img src="https://github.com/sudoitir/PersianLangToolbox/actions/workflows/maven.yml/badge.svg" alt="Java CI">
</a>
<br>

<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=code_smells"
         alt="Code Smells">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=ncloc"
         alt="Lines of Code">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=reliability_rating"
         alt="Reliability Rating">
</a>

<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=duplicated_lines_density"
         alt="Duplicated Lines (%)">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=vulnerabilities"
         alt="Vulnerabilities">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=bugs"
         alt="Bugs">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=security_rating"
         alt="Security Rating">
</a>
<a href="https://sonarcloud.io/summary/new_code?id=sudoitir_PersianLangToolbox">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=sudoitir_PersianLangToolbox&metric=sqale_rating"
         alt="Maintainability Rating">
</a>



Persian Lang Toolbox is a Java library for converting Persian numbers to words and providing additional options for currency.

[Go to Convert To Words Usage](#convert-to-words-usage)
<br>
[Go to Persian Number Converter](#persian-number-converter)

## Convert To Words Usage

To convert a number to Persian words, use the `NumberToPersianWords.convertToWords()` method with the following parameters:

```java
String result = NumberToPersianWords.convertToWords(stringNumber,
    new ConverterOptions(true, false, "ممیز",
        new CurrencyOptions("دلار", "$")
    )
);
```
<ul>
    <li>
        <code>stringNumber</code>:
        The number as a string that you want to convert.
    </li>
    <li><code>ConverterOptions</code>:
        The options for formatting the conversion, including:
        <ul>
            <li><code>true</code> for add currency word after converted word (e.g., "سی و پنج تومان") </li>
            <li><code>false</code> for excluding the fractional part (e.g., "سی و پنج") | <code>true</code> for including the fractional part (e.g., "سی و پنج ممیز یک دهم")</li>
            <li><code>String</code> for using the conjunction "ممیز" or "و" between integer and fractional parts (e.g., " سی و پنج ممیز یک دهم")..</li>
            <li><code>CurrencyOptions</code>: The options for formatting currency, including:
                <ul>
                    <li>"دلار" as the currency name (e.g., "صد دلار").</li>
                    <li>"$" as the currency symbol (e.g., "صد دلار $").</li>
                </ul>
            </li>
        </ul>
    </li>
</ul>

```java
stringNumber = "35.123";
String result = NumberToPersianWords.convertToWords(stringNumber,
        new ConverterOptions(true, true, "",
                new CurrencyOptions("دلار", "")
        )
);
System.out.println(result);
```

The above code will output:
```
سی و پنج دلار
```

```java
stringNumber = "1000";
String result = NumberToPersianWords.convertToWords(stringNumber);
System.out.println(result);
```

The above code will output:
```
یک هزار
```

More examples available in test cases.

## Persian Number Converter

The Persian Number Converter is a utility class that provides methods to convert between English and Persian numbers. It allows converting a given English number to its equivalent Persian representation and vice versa.

### Methods

```java
/**
 * Converts an English number to a Persian number.
 *
 * @param englishNumber The English number to be converted.
 * @return The Persian representation of the input English number.
 */
public static String convertToPersian(String englishNumber);
```
```java
/**
 * Converts a Persian number represented as a string to its equivalent English number representation.
 *
 * @param persianNumber The Persian number to convert.
 * @return The English number representation of the given Persian number.
 */
public static String convertToEnglish(String persianNumber);
```

```
// Example 1: convertToPersian
String englishNumber1 = "123";
String persianNumber1 = PersianNumberConverter.convertToPersian(englishNumber1);
System.out.println(persianNumber1);
// Output: ۱۲۳
```
```
// Example 2: convertToPersian
String englishNumber2 = "456";
String persianNumber2 = PersianNumberConverter.convertToPersian(englishNumber2);
System.out.println(persianNumber2);
// Output: ۴۵۶
```
```
// Example 1: convertToEnglish
String persianNumber1 = "۱۲۳";
String englishNumber1 = PersianNumberConverter.convertToEnglish(persianNumber1);
System.out.println(englishNumber1);
// Output: 123
```
```
// Example 2: convertToEnglish
String persianNumber2 = "۴۵۶";
String englishNumber2 = PersianNumberConverter.convertToEnglish(persianNumber2);
System.out.println(englishNumber2);
// Output: 456
```

