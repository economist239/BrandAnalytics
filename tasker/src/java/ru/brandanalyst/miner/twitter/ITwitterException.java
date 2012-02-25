package ru.brandanalyst.miner.twitter;

/**
 * @author Vanslov Evgeny (evans@yandex-team.ru)
 */
public class ITwitterException extends Exception {
    private static final long serialVersionUID = 2421790902701918007L;

    public ITwitterException(final String message) {
        super(message);
    }

    public ITwitterException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
