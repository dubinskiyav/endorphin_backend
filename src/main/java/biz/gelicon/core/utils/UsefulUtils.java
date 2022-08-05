package biz.gelicon.core.utils;

import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UsefulUtils {


    public static <T> Collector<T, ?, List<T>> toListReversed() {
        return Collectors.collectingAndThen(Collectors.toList(), l -> {
            Collections.reverse(l);
            return l;
        });
    }

    public static int indexOf(int[] data, int sample) {
        for (int i = 0; i < data.length; i++) {
            if(data[i]==sample) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Гененирует строку определенной длины
     * @param character
     * @param length
     * @return
     */
    public static String createString(char character, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, character);
        return new String(chars);
    }

    /**
     * Загрузает ресурс как строку
     * @param resource
     * @param charset
     * @return
     * @throws IOException
     */
    public static String load(Resource resource, Charset charset) {
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToString(is, charset);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(),ex);
        }
    }

    /**
     * Загрузает ресурс как строку UTF-8
     * @param resource
     * @return
     * @throws IOException
     */
    public static String load(Resource resource) {
        return load(resource,StandardCharsets.UTF_8);
    }


    public static String calculateHash(InputStream in) throws NoSuchAlgorithmException, IOException {
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        while ((count = in.read(buffer)) > 0) {
            digest.update(buffer, 0, count);
        }
        byte[] hash = digest.digest();
        return new BigInteger(1, hash).toString(16);
    }

    /**
     * Коллектор, допускающий null в value.
     * Автор https://stackoverflow.com/users/516188/emmanuel-touzery
     *
     * @param keyMapper
     * @param valueMapper
     * @param <T>
     * @param <K>
     * @param <U>
     * @return
     *
     *
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMapNullFriendly(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper) {
        @SuppressWarnings("unchecked")
        U none = (U) new Object();
        return Collectors.collectingAndThen(
                Collectors.<T, K, U> toMap(keyMapper,
                        valueMapper.andThen(v -> v == null ? none : v)), map -> {
                    map.replaceAll((k, v) -> v == none ? null : v);
                    return map;
                });
    }
}
