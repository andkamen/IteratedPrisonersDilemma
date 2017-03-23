package com.exceptions;

public class DuplicateEntryInStructureException extends RuntimeException {

    private static final String DUPLICATE_ENTRY =
            "'%s' is already registered as a %s.";

    public DuplicateEntryInStructureException(
            String entryName, String structureName) {
        super(String.format(DUPLICATE_ENTRY, entryName, structureName));
    }
}
