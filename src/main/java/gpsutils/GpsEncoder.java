package gpsutils;

/**
 * Gps Navigation Messages encoder.
 * @author Angelo G. Gaillet
 */
public final class GpsEncoder {
    private static final byte IONOSPHERIC_PARAMETERS_PAGE_18_SV_ID = 56;

    public static final int WORD_SIZE_BITS = 30;
    public static final int WORD_PADDING_BITS = 2;
    public static final int BYTE_AS_BITS = 8;
    private static final int GPS_CYCLE_WEEKS = 1024;

    private static final double POW_2_4 = Math.pow(2, 4);
    private static final double POW_2_11 = Math.pow(2, 11);
    private static final double POW_2_12 = Math.pow(2, 12);
    private static final double POW_2_14 = Math.pow(2, 14);
    private static final double POW_2_16 = Math.pow(2, 16);
    private static final double POW_2_NEG_5 = Math.pow(2, -5);
    private static final double POW_2_NEG_19 = Math.pow(2, -19);
    private static final double POW_2_NEG_24 = Math.pow(2, -24);
    private static final double POW_2_NEG_27 = Math.pow(2, -27);
    private static final double POW_2_NEG_29 = Math.pow(2, -29);
    private static final double POW_2_NEG_30 = Math.pow(2, -30);
    private static final double POW_2_NEG_31 = Math.pow(2, -31);
    private static final double POW_2_NEG_33 = Math.pow(2, -33);
    private static final double POW_2_NEG_43 = Math.pow(2, -43);
    private static final double POW_2_NEG_50 = Math.pow(2, -50);
    private static final double POW_2_NEG_55 = Math.pow(2, -55);

    private static final int L1_CA_SUBFRAME_LENGTH_BYTES = 40;

    // indexes in 0-based indexing
    private static final int SUBFRAMEID_INDEX = 49;
    private static final int SUBFRAMEID_LENGTH = 3;
    private static final int TOW_INDEX = 30;
    private static final int TOW_LENGTH = 17;
    private static final int IODC1_INDEX = 82;
    private static final int IODC1_LENGTH = 2;
    private static final int IODC2_INDEX = 210;
    private static final int IODC2_LENGTH = 8;
    private static final int WEEK_INDEX = 60;
    private static final int WEEK_LENGTH = 10;
    private static final int URA_INDEX = 72;
    private static final int URA_LENGTH = 4;
    private static final int SV_HEALTH_INDEX = 76;
    private static final int SV_HEALTH_LENGTH = 6;
    private static final int TGD_INDEX = 196;
    private static final int TGD_LENGTH = 8;
    private static final int AF2_INDEX = 240;
    private static final int AF2_LENGTH = 8;
    private static final int AF1_INDEX = 248;
    private static final int AF1_LENGTH = 16;
    private static final int AF0_INDEX = 270;
    private static final int AF0_LENGTH = 22;
    private static final int IODE1_INDEX = 60;
    private static final int IODE_LENGTH = 8;
    private static final int TOC_INDEX = 218;
    private static final int TOC_LENGTH = 16;
    private static final int CRS_INDEX = 68;
    private static final int CRS_LENGTH = 16;
    private static final int DELTA_N_INDEX = 90;
    private static final int DELTA_N_LENGTH = 16;
    private static final int M0_INDEX8 = 106;
    private static final int M0_INDEX24 = 120;
    private static final int CUC_INDEX = 150;
    private static final int CUC_LENGTH = 16;
    private static final int E_INDEX8 = 166;
    private static final int E_INDEX24 = 180;
    private static final int CUS_INDEX = 210;
    private static final int CUS_LENGTH = 16;
    private static final int A_INDEX8 = 226;
    private static final int A_INDEX24 = 240;
    private static final int TOE_INDEX = 270;
    private static final int TOE_LENGTH = 16;
    private static final int IODE2_INDEX = 270;
    private static final int CIC_INDEX = 60;
    private static final int CIC_LENGTH = 16;
    private static final int O0_INDEX8 = 76;
    private static final int O0_INDEX24 = 90;
    private static final int O_INDEX8 = 196;
    private static final int O_INDEX24 = 210;
    private static final int ODOT_INDEX = 240;
    private static final int ODOT_LENGTH = 24;
    private static final int CIS_INDEX = 120;
    private static final int CIS_LENGTH = 16;
    private static final int I0_INDEX8 = 136;
    private static final int I0_INDEX24 = 150;
    private static final int CRC_INDEX = 180;
    private static final int CRC_LENGTH = 16;
    private static final int IDOT_INDEX = 278;
    private static final int IDOT_LENGTH = 14;
    private static final int A0_INDEX = 68;
    private static final int A_B_LENGTH = 8;
    private static final int A1_INDEX = 76;
    private static final int A2_INDEX = 90;
    private static final int A3_INDEX = 98;
    private static final int B0_INDEX = 106;
    private static final int B1_INDEX = 120;
    private static final int B2_INDEX = 128;
    private static final int B3_INDEX = 136;
    private static final int WN_LS_INDEX = 226;
    private static final int DELTA_T_LS_INDEX = 240;
    private static final int TOT_LS_INDEX = 218;
    private static final int DN_LS_INDEX = 256;
    private static final int WNF_LS_INDEX = 248;
    private static final int DELTA_TF_LS_INDEX = 270;
    private static final int I0UTC_INDEX8 = 210;
    private static final int I0UTC_INDEX24 = 180;
    private static final int I1UTC_INDEX = 150;
    private static final int IONOSPHERIC_PARAMETERS_PAGE_INDEX = 62;
    private static final int IONOSPHERIC_PARAMETERS_PAGE_LENGTH = 6;

    public byte[] createFirstSubframe(GpsEphemeris ephemeris, int tow) {
        byte[] result = new byte[L1_CA_SUBFRAME_LENGTH_BYTES];
        toBits(SUBFRAMEID_INDEX, SUBFRAMEID_LENGTH, 1, result);
        toBits(TOW_INDEX, TOW_LENGTH, tow, result);
        toBits(IODC1_INDEX, IODC1_LENGTH, ephemeris.iodc >> 8, result);
        toBits(IODC2_INDEX, IODC2_LENGTH, ephemeris.iodc, result);
        toBits(WEEK_INDEX, WEEK_LENGTH, ephemeris.week % GPS_CYCLE_WEEKS, result);
        toBits(URA_INDEX, URA_LENGTH, ephemeris.ura, result);
        toBits(SV_HEALTH_INDEX, SV_HEALTH_LENGTH, ephemeris.svHealth, result);
        toBits(TGD_INDEX, TGD_LENGTH, (int) (ephemeris.tgd / POW_2_NEG_31), result);
        toBits(TOC_INDEX, TOC_LENGTH, (int) (ephemeris.toc / POW_2_4), result);
        toBits(AF2_INDEX, AF2_LENGTH, (int) (ephemeris.af2 / POW_2_NEG_55), result);
        toBits(AF1_INDEX, AF1_LENGTH, (int) (ephemeris.af1 / POW_2_NEG_43), result);
        toBits(AF0_INDEX, AF0_LENGTH, (int) (ephemeris.af0 / POW_2_NEG_31), result);
        return result;
    }

    public byte[] createSecondSubframe(GpsEphemeris ephemeris, int tow){
        byte[] result = new byte[L1_CA_SUBFRAME_LENGTH_BYTES];
        toBits(SUBFRAMEID_INDEX, SUBFRAMEID_LENGTH, 2, result);
        toBits(TOW_INDEX, TOW_LENGTH, tow, result);
        toBits(IODE1_INDEX, IODE_LENGTH, ephemeris.iode, result);
        toBits(CRS_INDEX, CRS_LENGTH, (int) (ephemeris.crs / POW_2_NEG_5), result);
        toBits(DELTA_N_INDEX, DELTA_N_LENGTH, (int) (ephemeris.deltaN / POW_2_NEG_43 / Math.PI), result);
        int m0 = (int) (ephemeris.m0 / POW_2_NEG_31 / Math.PI);
        toBits(M0_INDEX8, 8, m0 >> 24, result);
        toBits(M0_INDEX24, 24, m0, result);
        toBits(CUC_INDEX, CUC_LENGTH, (int) (ephemeris.cuc / POW_2_NEG_29), result);
        int e = (int) (ephemeris.e / POW_2_NEG_33);
        toBits(E_INDEX8, 8, e >> 24, result);
        toBits(E_INDEX24, 24, e, result);
        toBits(CUS_INDEX, CUS_LENGTH, (int) (ephemeris.cus / POW_2_NEG_29), result);
        int a = (int) (ephemeris.rootOfA / POW_2_NEG_19);
        toBits(A_INDEX8, 8, a >> 24, result);
        toBits(A_INDEX24, 24, a, result);
        toBits(TOE_INDEX, TOE_LENGTH, (int) (ephemeris.toe / POW_2_4), result);
        return result;
    }

    public byte[] createThirdSubframe(GpsEphemeris ephemeris, int tow){
        byte[] result = new byte[L1_CA_SUBFRAME_LENGTH_BYTES];
        toBits(SUBFRAMEID_INDEX, SUBFRAMEID_LENGTH, 3, result);
        toBits(TOW_INDEX, TOW_LENGTH, tow, result);
        toBits(IODE2_INDEX, IODE_LENGTH, ephemeris.iode, result);
        toBits(CIC_INDEX, CIC_LENGTH, (int) (ephemeris.cic / POW_2_NEG_29), result);
        int o0 = (int) (ephemeris.omega0 / POW_2_NEG_31 / Math.PI);
        toBits(O0_INDEX8, 8, o0 >> 24, result);
        toBits(O0_INDEX24, 24, o0, result);
        int o = (int) (ephemeris.omega / POW_2_NEG_31 / Math.PI);
        toBits(O_INDEX8, 8, o >> 24, result);
        toBits(O_INDEX24, 24, o, result);
        toBits(ODOT_INDEX, ODOT_LENGTH, (int) (ephemeris.omegaDot / POW_2_NEG_43 / Math.PI), result);
        toBits(CIS_INDEX, CIS_LENGTH, (int) (ephemeris.cis / POW_2_NEG_29), result);
        int i0 = (int) (ephemeris.i0 / POW_2_NEG_31 / Math.PI);
        toBits(I0_INDEX8, 8, i0 >> 24, result);
        toBits(I0_INDEX24, 24, i0, result);
        toBits(CRC_INDEX, CRC_LENGTH, (int) (ephemeris.crc / POW_2_NEG_5), result);
        toBits(IDOT_INDEX, IDOT_LENGTH, (int) (ephemeris.iDot / POW_2_NEG_43 / Math.PI), result);
        return result;
    }

    public byte[] createFourthSubframe(GpsSupportData supportData, int tow){
        byte[] result = new byte[L1_CA_SUBFRAME_LENGTH_BYTES];
        toBits(SUBFRAMEID_INDEX, SUBFRAMEID_LENGTH, 4, result);
        toBits(TOW_INDEX, TOW_LENGTH, tow, result);
        toBits(IONOSPHERIC_PARAMETERS_PAGE_INDEX, IONOSPHERIC_PARAMETERS_PAGE_LENGTH, IONOSPHERIC_PARAMETERS_PAGE_18_SV_ID, result);
        ionosphericParamToBits(supportData.ionoAlpha, A0_INDEX, POW_2_NEG_30, A1_INDEX, POW_2_NEG_27, A2_INDEX, POW_2_NEG_24, A3_INDEX, result);
        ionosphericParamToBits(supportData.ionoBeta, B0_INDEX, POW_2_11, B1_INDEX, POW_2_14, B2_INDEX, POW_2_16, B3_INDEX, result);
        int a0UTC = (int) (supportData.a0UTC / POW_2_NEG_30);
        toBits(I0UTC_INDEX8, 8, a0UTC, result);
        toBits(I0UTC_INDEX24, 24, a0UTC >> 8, result);
        toBits(I1UTC_INDEX, 24, (int) (supportData.a1UTC / POW_2_NEG_50), result);
        toBits(TOT_LS_INDEX, A_B_LENGTH, (int) (supportData.tot / POW_2_12), result);
        toBits(WN_LS_INDEX, A_B_LENGTH, supportData.wnt, result);
        toBits(DELTA_T_LS_INDEX, A_B_LENGTH, supportData.tls, result);
        toBits(WNF_LS_INDEX, A_B_LENGTH, supportData.wnlsf, result);
        toBits(DN_LS_INDEX, A_B_LENGTH, supportData.dn, result);
        toBits(DELTA_TF_LS_INDEX, A_B_LENGTH, supportData.tlsf, result);
        return result;
    }

    private void ionosphericParamToBits(double[] p, int index0, double pow_a, int index1, double pow_b, int index2, double pow_c, int index3, byte[] target) {
        toBits(index0, A_B_LENGTH, (int) (p[0] / pow_a), target);
        toBits(index1, A_B_LENGTH, (int) (p[1] / pow_b), target);
        toBits(index2, A_B_LENGTH, (int) (p[2] / pow_c), target);
        toBits(index3, A_B_LENGTH, (int) (p[3] / pow_c), target);
    }

    /**
     * Adds the bits corresponding to the given data in the given position of the target considering padding.
     * If the int value is larger than what can be represented in the given length the MSBs are truncated.
     * @param index 0-indexing index of the value in a non-padded format
     * @param length the number of bits occupied by the value
     * @param data the value to be written
     * @param target the target byte array
     */
    private void toBits(int index, int length, int data, byte[] target) {
        for (int i = 0 ; i < length; ++i) {
            int workingIndex = index + i;
            int wordIndex = workingIndex / WORD_SIZE_BITS;

            // account for 2 bit padding for every 30bit word
            workingIndex += (wordIndex + 1) * WORD_PADDING_BITS;

            int byteIndex = workingIndex / BYTE_AS_BITS;
            int bitIndexInByte = BYTE_AS_BITS - (workingIndex % BYTE_AS_BITS) - 1;

            int bit = (data >> length - i - 1) & 1;
            target[byteIndex] |= bit << bitIndexInByte;
        }
    }

}
