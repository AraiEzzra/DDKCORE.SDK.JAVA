package global.eska.ddk.keygen.utils;

import java.math.BigDecimal;

public interface Etalon {
    static final String PASSPHRASE =
            "assume dynamic woman audit any join category castle economy assume balcony inflict";

    static final String PUBLIC_KEY_HEX = "f4cd70d17938f5953ea4317d461bd092592b79a086222657fc00abc7d01dd9cc";

    final BigDecimal ADDRESS = new BigDecimal("4793028192854197360");

    static final byte[] PASSPHRASE_HASH = {
            6, 36, (byte) 190, (byte) 241, 84, (byte) 176, (byte) 181, 125, (byte) 252, (byte) 165, 63, (byte) 203, 94,
            (byte) 142, 51, 119, (byte) 194, 52, (byte) 209, 120, 34, 53, 38, 105, 66, (byte) 203, (byte) 223, 31,
            (byte) 141, 112, 3, (byte) 210
    };

    static final byte[] PUBLIC_KEY_BYTES = {
            (byte) 244, (byte) 205, 112, (byte) 209, 121, 56, (byte) 245, (byte) 149, 62, (byte) 164, 49, 125, 70, 27,
            (byte) 208, (byte) 146, 89, 43, 121, (byte) 160, (byte) 134, 34, 38, 87, (byte) 252, 0, (byte) 171, (byte) 199,
            (byte) 208, 29, (byte) 217, (byte) 204
    };

    static final byte[] PRIVATE_KEY_BYTES = {
            6, 36, (byte) 190, (byte) 241, 84, (byte) 176, (byte) 181, 125, (byte) 252, (byte) 165, 63, (byte) 203, 94,
            (byte) 142, 51, 119, (byte) 194, 52, (byte) 209, 120, 34, 53, 38, 105, 66, (byte) 203, (byte) 223, 31,
            (byte) 141, 112, 3, (byte) 210, (byte) 244, (byte) 205, 112, (byte) 209, 121, 56, (byte) 245, (byte) 149,
            62, (byte) 164, 49, 125, 70, 27, (byte) 208, (byte) 146, 89, 43, 121, (byte) 160, (byte) 134, 34, 38, 87,
            (byte) 252, 0, (byte) 171, (byte) 199, (byte) 208, 29, (byte) 217, (byte) 204
    };
}
