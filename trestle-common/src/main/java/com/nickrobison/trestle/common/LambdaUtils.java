package com.nickrobison.trestle.common;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by nrobison on 9/15/16.
 */
public class LambdaUtils {
    /**
     * Takes a list of completable futures and sequences them into a single future with a list of results
     * @param futures - List of Completable Futures of Type T
     * @param <T> - Type of Future
     * @return - Completable Future of a List of T
     */
    public static <T>CompletableFuture<List<T>> sequenceCompletableFutures(List<CompletableFuture<T>> futures) {
        final CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return voidCompletableFuture.thenApply(v ->
            futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
        );
    }
}
