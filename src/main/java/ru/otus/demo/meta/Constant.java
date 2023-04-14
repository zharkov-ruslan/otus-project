package ru.otus.demo.meta;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;

/**
 * Общий класс констант.
 *
 * @author Руслан Жарков
 */
@NoArgsConstructor(access = PACKAGE)
public class Constant {

    public static final String ISO_8601_EXTENDED_DATETIME_Z_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}
