package gpsutils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author A.G. Gaillet
 */
class GpsEncoderTest {

    /**
     * The Gps Encoder shall generate a spoofed GPS message in Android-standard bytecode as described in Annex 2 to the SDD.
     * Tested via subsequent encoding and decoding of all subframes and equality checking.
     */
    @org.junit.jupiter.api.Test
    void SW_6(){
        // TODO implement.
        // Generate ephemeris and support data
        // Encode messages
        // Decode messages
        // Check semantic equality (.equals) of original and resulting objects
    }

    /**
     * Gps Navigation Messages decoder.
     * Based on the work done on gps measurement tools by the <a href="https://github.com/google/gps-measurement-tools">Android Open Source Project</a>.
     * The Navigation Messages to be decoded shall follow the standard navigation message format of android as described <a href="https://www.euspa.europa.eu/system/files/reports/gnss_raw_measurement_web_0.pdf">here</a>.
     * Test GPS messages can be obtained via <a href="https://play.google.com/store/apps/details?id=com.google.android.apps.location.gps.gnsslogger&pli=1">the GnssLogger App</a> using a <a href="https://developer.android.com/develop/sensors-and-location/sensors/gnss">supported device</a>
     *
     * @author Angelo G. Gaillet
     */
    static class GpsDecoder {
        private static final byte IONOSPHERIC_PARAMETERS_PAGE_18_SV_ID = 56;

        public static final int WORD_SIZE_BITS = 30;
        public static final int WORD_PADDING_BITS = 2;
        public static final int BYTE_AS_BITS = 8;
        private static final int GPS_CYCLE_WEEKS = 1024;
        private static final int IODE_TO_IODC_MASK = 0xFF;

        public static final int SUBFRAME_1 = (1); // 1 << 0
        public static final int SUBFRAME_2 = (1 << 1);
        public static final int SUBFRAME_3 = (1 << 2);
        public static final int SUBFRAME_4 = (1 << 3);
        public static final int SUBFRAME_5 = (1 << 4);

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
        private static final double POW_2_NEG_55 = Math.pow(2, -55);

        private static final long INTEGER_RANGE = 0xFFFFFFFFL;
        private static final long GPS_EPOCH_AS_UNIX_EPOCH_MS = TimeUnit.DAYS.toMillis(3657); // 3657 is the number of days between the unix epoch and GPS epoch as the GPS epoch started on Jan 6, 1980
        private static final long GPS_CYCLE_MS = TimeUnit.DAYS.toMillis(7168); // A GPS Cycle is 1024 weeks, or 7168 days

        /** Maximum possible number of GPS satellites */
        public static final int MAX_NUMBER_OF_SATELLITES = 32;

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

        /** Partially decoded intermediate ephemerides */
        private final IntermediateEphemeris[] partiallyDecodedIntermediateEphemerides = new IntermediateEphemeris[MAX_NUMBER_OF_SATELLITES];

        /** Fully decoded intermediate ephemerides */
        private final IntermediateEphemeris[] fullyDecodedIntermediateEphemerides = new IntermediateEphemeris[MAX_NUMBER_OF_SATELLITES];

        private final GpsSupportData supportData = new GpsSupportData();

        @SuppressWarnings("unused")
        public ArrayList<GpsEphemeris> getEphemerides(){
            ArrayList<GpsEphemeris> gpsEphemerisList = new ArrayList<>();
            for (int i = 0; i < MAX_NUMBER_OF_SATELLITES; i++) {
                if (fullyDecodedIntermediateEphemerides[i] != null) {
                    gpsEphemerisList.add(fullyDecodedIntermediateEphemerides[i]);
                }
            }
            return gpsEphemerisList;
        }

        @SuppressWarnings("unused")
        public GpsSupportData getSupportData(){
            return supportData;
        }

        /**
         * Handles a fresh GPS L1 C/A Navigation Message. The message is in its raw format.
         * A navigation messages consists in a GPS subframe. A subframe consists of 10 30-bit words. Each word is fit into
         * the last 30 bits of a 4-byte word (setting B31 and B32 to 0), with the Most Significant Bit first. The total
         * message is 40 bytes long.
         */
        @SuppressWarnings("unused")
        public void elaborateNavMessage(byte prn, byte[] rawData) throws Exception{
            int subframeId = extractBits(SUBFRAMEID_INDEX, SUBFRAMEID_LENGTH, rawData);
            elaborateNavMessage(prn, subframeId, rawData);
        }

        /** Handles a fresh GPS L1 C/A Navigation Message of the given subframe type. The message is in its raw format. */
        private void elaborateNavMessage(byte prn, int id, byte[] rawData) throws Exception {
            if(rawData == null || rawData.length != L1_CA_SUBFRAME_LENGTH_BYTES){
                throw new Exception("Invalid length of rawData for L1 C/A");
            }
            synchronized (fullyDecodedIntermediateEphemerides) {
                switch (id) {
                    case 1:
                        handleFirstSubframe(prn, rawData);
                        break;
                    case 2:
                        handleSecondSubframe(prn, rawData);
                        break;
                    case 3:
                        handleThirdSubframe(prn, rawData);
                        break;
                    case 4:
                        handleFourthSubframe(rawData);
                        break;
                    case 5:
                        break;
                    default:
                        // invalid message id
                        throw new IllegalArgumentException("Invalid Subframe ID: " + id);
                }
            }
        }

        /**
         * Handles the first navigation message subframe which contains satellite clock correction
         * parameters, GPS date (week number) plus satellite status and health.
         */
        private void handleFirstSubframe(byte prn, byte[] rawData) {
            int iodc = extractBits(IODC1_INDEX, IODC1_LENGTH, rawData) << 8; // Issue Of Data Clock
            iodc |= extractBits(IODC2_INDEX, IODC2_LENGTH, rawData); // a |= b is a = a | b where | is the bitwise or

            IntermediateEphemeris iEphem = findIntermediateEphemerisToUpdate(prn, SUBFRAME_1, iodc);
            if (iEphem == null) {
                // we are up-to-date
                return;
            }

            iEphem.iodc = iodc;

            // the navigation message contains a modulo-1024 week number
            int week = extractBits(WEEK_INDEX, WEEK_LENGTH, rawData);
            week = getGpsWeekWithRollover(week);
            iEphem.week = week;

            iEphem.ura = extractBits(URA_INDEX, URA_LENGTH, rawData);

            iEphem.svHealth = extractBits(SV_HEALTH_INDEX, SV_HEALTH_LENGTH, rawData); // Satellite health

            iEphem.tgd = ((byte) extractBits(TGD_INDEX, TGD_LENGTH, rawData)) * POW_2_NEG_31; // Estimated group delay differential

            iEphem.toc = extractBits(TOC_INDEX, TOC_LENGTH, rawData) * POW_2_4; // Scaled toc (Satellite clock correction)

            // Polynomial coefficients of clock correction: af2 drift rate, af1 SV clock drift, af0 SV clock bias
            iEphem.af2 = ((byte) extractBits(AF2_INDEX, AF2_LENGTH, rawData)) * POW_2_NEG_55;

            iEphem.af1 = ((short) extractBits(AF1_INDEX, AF1_LENGTH, rawData)) * POW_2_NEG_43;

            iEphem.af0 = getTwoComplement(extractBits(AF0_INDEX, AF0_LENGTH, rawData), AF0_LENGTH) * POW_2_NEG_31; // af0 is a 22-bit two's complement number

            updateDecodedState(prn, SUBFRAME_1, iEphem);
        }

        /** Handles the second navigation message subframe which contains satellite ephemeris */
        private void handleSecondSubframe(byte prn, byte[] rawData) {
            int iode = extractBits(IODE1_INDEX, IODE_LENGTH, rawData); // Issue of Data Ephemeris

            IntermediateEphemeris iEphem = findIntermediateEphemerisToUpdate(prn, SUBFRAME_2, iode);
            if (iEphem == null) return; // nothing to update

            iEphem.iode = iode;

            short crs = (short) extractBits(CRS_INDEX, CRS_LENGTH, rawData);
            iEphem.crs = crs * POW_2_NEG_5;

            short deltaN = (short) extractBits(DELTA_N_INDEX, DELTA_N_LENGTH, rawData);
            iEphem.deltaN = deltaN * POW_2_NEG_43 * Math.PI;

            int m0 = (int) buildUnsigned32BitsWordFrom8And24Words(M0_INDEX8, M0_INDEX24, rawData);
            iEphem.m0 = m0 * POW_2_NEG_31 * Math.PI;

            short cuc = (short) extractBits(CUC_INDEX, CUC_LENGTH, rawData);
            iEphem.cuc = cuc * POW_2_NEG_29;

            // an unsigned 32 bit value
            long e = buildUnsigned32BitsWordFrom8And24Words(E_INDEX8, E_INDEX24, rawData);
            iEphem.e = e * POW_2_NEG_33;

            short cus = (short) extractBits(CUS_INDEX, CUS_LENGTH, rawData);
            iEphem.cus = cus * POW_2_NEG_29;

            // an unsigned 32 bit value
            long a = buildUnsigned32BitsWordFrom8And24Words(A_INDEX8, A_INDEX24, rawData);
            iEphem.rootOfA = a * POW_2_NEG_19;

            int toe = extractBits(TOE_INDEX, TOE_LENGTH, rawData);
            iEphem.toe = toe * POW_2_4;

            updateDecodedState(prn, SUBFRAME_2, iEphem);
        }

        /** Handles the third navigation message subframe which contains satellite ephemeris */
        private void handleThirdSubframe(byte prn, byte[] rawData) {

            int iode = extractBits(IODE2_INDEX, IODE_LENGTH, rawData);

            IntermediateEphemeris intermediateEphemeris = findIntermediateEphemerisToUpdate(prn, SUBFRAME_3, iode);
            if (intermediateEphemeris == null) return; // nothing to update

            intermediateEphemeris.iode = iode;

            short cic = (short) extractBits(CIC_INDEX, CIC_LENGTH, rawData);
            intermediateEphemeris.cic = cic * POW_2_NEG_29;

            int o0 = (int) buildUnsigned32BitsWordFrom8And24Words(O0_INDEX8, O0_INDEX24, rawData);
            intermediateEphemeris.omega0 = o0 * POW_2_NEG_31 * Math.PI;

            int o = (int) buildUnsigned32BitsWordFrom8And24Words(O_INDEX8, O_INDEX24, rawData);
            intermediateEphemeris.omega = o * POW_2_NEG_31 * Math.PI;

            int odot = extractBits(ODOT_INDEX, ODOT_LENGTH, rawData);
            odot = getTwoComplement(odot, ODOT_LENGTH);

            intermediateEphemeris.omegaDot = odot * POW_2_NEG_43 * Math.PI;

            short cis = (short) extractBits(CIS_INDEX, CIS_LENGTH, rawData);
            intermediateEphemeris.cis = cis * POW_2_NEG_29;

            int i0 = (int) buildUnsigned32BitsWordFrom8And24Words(I0_INDEX8, I0_INDEX24, rawData);
            intermediateEphemeris.i0 = i0 * POW_2_NEG_31 * Math.PI;

            short crc = (short) extractBits(CRC_INDEX, CRC_LENGTH, rawData);
            intermediateEphemeris.crc = crc * POW_2_NEG_5;

            // a 14-bit two's complement number
            int idot = extractBits(IDOT_INDEX, IDOT_LENGTH, rawData);
            idot = getTwoComplement(idot, IDOT_LENGTH);
            intermediateEphemeris.iDot = idot * POW_2_NEG_43 * Math.PI;

            updateDecodedState(prn, SUBFRAME_3, intermediateEphemeris);
        }

        /**
         * Subframe four provides ionospheric model parameters , UTC information, part of the almanac, and
         * indications whether the Anti-Spoofing, is activated or not.
         *
         * <p>For now, only the ionospheric parameters are parsed.
         */
        private void handleFourthSubframe(byte[] rawData) {
            byte pageId = (byte) extractBits(62, 6, rawData);
            if (pageId != IONOSPHERIC_PARAMETERS_PAGE_18_SV_ID) {
                // We only care to decode ionospheric parameters for now
                return;
            }
            supportData.ionoAlpha = elaborateIonosphereParams(rawData, A0_INDEX, POW_2_NEG_30, A1_INDEX, POW_2_NEG_27, A2_INDEX, POW_2_NEG_24, A3_INDEX);
            supportData.ionoBeta = elaborateIonosphereParams(rawData, B0_INDEX, POW_2_11, B1_INDEX, POW_2_14, B2_INDEX, POW_2_16, B3_INDEX);
            supportData.a0UTC = buildSigned32BitsWordFrom8And24WordsWith8bitsLSB(I0UTC_INDEX8, I0UTC_INDEX24, rawData) * Math.pow(2, -30);
            supportData.a1UTC = getTwoComplement(extractBits(I1UTC_INDEX, 24, rawData), 24) * Math.pow(2, -50);
            supportData.tot = (short) (extractBits(TOT_LS_INDEX, A_B_LENGTH, rawData) * POW_2_12);
            supportData.wnt = (short) extractBits(WN_LS_INDEX, A_B_LENGTH, rawData);
            supportData.tls = (short) extractBits(DELTA_T_LS_INDEX, A_B_LENGTH, rawData);
            supportData.wnlsf = (short) extractBits(WNF_LS_INDEX, A_B_LENGTH, rawData);
            supportData.dn = (short) extractBits(DN_LS_INDEX, A_B_LENGTH, rawData);
            supportData.tlsf = (short) extractBits(DELTA_TF_LS_INDEX, A_B_LENGTH, rawData);
        }

        private double[] elaborateIonosphereParams(byte[] rawData, int index0, double pow_a, int index1, double pow_b, int index2, double pow_c, int index3) {
            double[] p = new double[4];
            p[0] = ((byte) extractBits(index0, A_B_LENGTH, rawData)) * pow_a;
            p[1] = ((byte) extractBits(index1, A_B_LENGTH, rawData)) * pow_b;
            p[2] = ((byte) extractBits(index2, A_B_LENGTH, rawData)) * pow_c;
            p[3] = ((byte) extractBits(index3, A_B_LENGTH, rawData)) * pow_c;
            return p;
        }

        /**
         * Updates the {@link IntermediateEphemeris} with the decoded status of the current subframe.
         * Moreover, update the {@code partiallyDecodedIntermediateEphemerides} list and {@code
         * fullyDecodedIntermediateEphemerides} list
         */
        private void updateDecodedState(byte prn, int decodedSubframeNumber, IntermediateEphemeris intermediateEphemeris) {
            intermediateEphemeris.reportDecodedSubframe(decodedSubframeNumber);
            if (intermediateEphemeris.isFullyDecoded()) {
                partiallyDecodedIntermediateEphemerides[prn - 1] = null;
                fullyDecodedIntermediateEphemerides[prn - 1] = intermediateEphemeris;
            } else {
                partiallyDecodedIntermediateEphemerides[prn - 1] = intermediateEphemeris;
            }
        }

        /**
         * Extracts the requested bits from the raw stream.
         *
         * @param index Zero-based index of the first bit to extract.
         * @param length The length of the stream of bits to extract.
         * @param rawData The stream to extract data from.
         * @return The bits requested always shifted to the least significant positions.
         */
        private static int extractBits(int index, int length, byte[] rawData) {
            int result = 0;

            for (int i = 0; i < length; ++i) {
                int workingIndex = index + i;

                int wordIndex = workingIndex / WORD_SIZE_BITS;
                // account for 2 bit padding for every 30bit word
                workingIndex += (wordIndex + 1) * WORD_PADDING_BITS;
                int byteIndex = workingIndex / BYTE_AS_BITS;
                int byteOffset = workingIndex % BYTE_AS_BITS;

                byte raw = rawData[byteIndex];
                // account for zero-based indexing
                int shiftOffset = BYTE_AS_BITS - 1 - byteOffset;
                int mask = 1 << shiftOffset;
                int bit = raw & mask;
                bit >>= shiftOffset;

                // account for zero-based indexing
                result |= bit << length - 1 - i;
            }
            return result;
        }

        /**
         * Extracts an unsigned 32 bit word where the word is partitioned 8/24 bits.
         *
         * @param index8 The index of the first 8 bits used.
         * @param index24 The index of the last 24 bits used.
         * @param rawData The stream to extract data from.
         * @return The bits requested represented as a long and stored in the least significant positions.
         */
        private static long buildUnsigned32BitsWordFrom8And24Words(int index8, int index24, byte[] rawData) {
            long result = (long) extractBits(index8, 8, rawData) << 24;
            result |= extractBits(index24, 24, rawData);
            return result;
        }

        /**
         * Extracts a signed 32 bit word where the word is partitioned 8/24 bits with LSB first.
         *
         * @param rawData The stream to extract data from.
         * @return The bits requested represented as an int and stored in the least significant positions.
         */
        @SuppressWarnings("all")
        private static int buildSigned32BitsWordFrom8And24WordsWith8bitsLSB(int index8, int index24, byte[] rawData) {
            int result = extractBits(index24, 24, rawData) << 8;
            result |= extractBits(index8, 8, rawData);
            return result;
        }

        /**
         * Calculates the 2s complement for a specific number of bits of a given value
         *
         * @param value The set of bits to translate.
         * @param bits The number of bits to consider.
         * @return The calculated 2s complement.
         */
        private static int getTwoComplement(int value, int bits) {
            int msbMask = 1 << bits - 1;
            int msb = value & msbMask;
            if (msb == 0) {
                // the value is positive
                return value;
            }

            int valueBitMask = (1 << bits) - 1;
            int extendedSignMask = (int) INTEGER_RANGE - valueBitMask;
            return value | extendedSignMask;
        }

        /**
         * Calculates the GPS week with rollovers. A rollover happens every 1024 weeks, beginning from GPS
         * epoch (January 6, 1980).
         *
         * @param gpsWeek The modulo-1024 GPS week.
         * @return The absolute GPS week.
         */
        private static int getGpsWeekWithRollover(int gpsWeek) {
            long nowMs = System.currentTimeMillis();
            long elapsedTimeFromGpsEpochMs = nowMs - GPS_EPOCH_AS_UNIX_EPOCH_MS;
            long rolloverCycles = elapsedTimeFromGpsEpochMs / GPS_CYCLE_MS;
            int rolloverWeeks = (int) rolloverCycles * GPS_CYCLE_WEEKS;
            return gpsWeek + rolloverWeeks;
        }

        /**
         * Finds an {@link IntermediateEphemeris} that can be updated by the given data. The pseudocode is
         * as follows:
         *
         * <p>if a fully decoded message is available and matches, there is no need to update
         *
         * <p>if a partially decoded message is available and matches, there is no need to update
         *
         * <p>if the provided issueOfData matches intermediate partially decoded state, update in place
         *
         * <p>otherwise, start a new decoding 'session' for the prn
         *
         * @param prn The prn to update
         * @param subframe The subframe available to update
         * @param issueOfData The issueOfData associated with the given subframe
         * @return a {@link IntermediateEphemeris} to update with the available data, {@code null} if
         *     there is no need to update a {@link IntermediateEphemeris}.
         */
        private IntermediateEphemeris findIntermediateEphemerisToUpdate(byte prn, int subframe, int issueOfData) {
            // find out if we have fully decoded up-to-date ephemeris first
            IntermediateEphemeris fullyDecodedIntermediateEphemeris =
                    this.fullyDecodedIntermediateEphemerides[prn - 1];
            if (fullyDecodedIntermediateEphemeris != null && fullyDecodedIntermediateEphemeris.isSubframeDecodedAndCurrent(prn, subframe, issueOfData)) {
                return null;
            }

            // find out next if there is a partially decoded intermediate state we can continue working on
            IntermediateEphemeris partiallyDecodedIntermediateEphemeris =
                    this.partiallyDecodedIntermediateEphemerides[prn - 1];
            if (partiallyDecodedIntermediateEphemeris == null) {
                // no intermediate partially decoded state, we need to start a decoding 'session'
                return new IntermediateEphemeris(prn);
            }

            if (partiallyDecodedIntermediateEphemeris.isSubframeDecodedAndCurrent(prn, subframe, issueOfData)){
                return null;
            }

            if (partiallyDecodedIntermediateEphemeris.hasDecodedSubframe(subframe) && partiallyDecodedIntermediateEphemeris.issueMatches(subframe, issueOfData)) {
                // the navigation message has changed, we need to start over
                return new IntermediateEphemeris(prn);
            }

            int intermediateIode = Integer.MAX_VALUE;
            boolean intermediateHasIode = false;

            if (partiallyDecodedIntermediateEphemeris.hasDecodedSubframe(SUBFRAME_1)) {
                intermediateHasIode = true;
                intermediateIode = partiallyDecodedIntermediateEphemeris.iodc & IODE_TO_IODC_MASK;
            }
            if (partiallyDecodedIntermediateEphemeris.hasDecodedSubframe(SUBFRAME_2)
                    || partiallyDecodedIntermediateEphemeris.hasDecodedSubframe(SUBFRAME_3)) {
                intermediateHasIode = true;
                intermediateIode = partiallyDecodedIntermediateEphemeris.iode;
            }

            boolean canContinueDecoding;
            int iode;
            switch (subframe) {
                case SUBFRAME_1 -> {
                    iode = issueOfData & IODE_TO_IODC_MASK;
                    canContinueDecoding = !intermediateHasIode || (intermediateIode == iode);
                }
                case SUBFRAME_2, SUBFRAME_3 -> {
                    iode = issueOfData;
                    canContinueDecoding = !intermediateHasIode || (intermediateIode == iode);
                }
                case SUBFRAME_4, SUBFRAME_5 ->
                    // always continue decoding for subframes 4-5
                        canContinueDecoding = true;
                default -> throw new IllegalStateException("invalid subframe requested: " + subframe);
            }

            if (canContinueDecoding) {
                return partiallyDecodedIntermediateEphemeris;
            }
            return new IntermediateEphemeris(prn);
        }

        /**
         * A representation of an intermediate ephemeris that can be fully decoded or partially decoded.
         */
        private static class IntermediateEphemeris extends GpsEphemeris {

            private int subframesDecoded;

            public IntermediateEphemeris(byte prn) {
                this.prn = prn;
            }

            public void reportDecodedSubframe(int subframe) {
                subframesDecoded |= subframe;
            }

            private boolean hasDecodedSubframe(int subframe) {
                return (subframesDecoded & subframe) == subframe;
            }

            public boolean isFullyDecoded() {
                return hasDecodedSubframe(SUBFRAME_1)
                        && hasDecodedSubframe(SUBFRAME_2)
                        && hasDecodedSubframe(SUBFRAME_3);
            }

            public boolean issueMatches(int subframe, int issueOfData){
                return switch (subframe) {
                    case SUBFRAME_1 -> this.iodc == issueOfData;
                    case SUBFRAME_2, SUBFRAME_3 -> this.iode == issueOfData;
                    case SUBFRAME_4, SUBFRAME_5 -> true; // subframes 4-5 do not have IOD to match, so we assume they always match
                    default -> throw new IllegalArgumentException("Invalid subframe provided: " + subframe);
                };
            }

            /**
             * @return {@code true} if the requested subframe has been decoded in the intermediate state, {@code false} otherwise.
             */
            public boolean isSubframeDecodedAndCurrent(byte prn, int subframe, int issueOfData) {
                if (this.prn != prn) return false;
                return issueMatches(subframe, issueOfData) && hasDecodedSubframe(subframe);
            }
        }
    }

}