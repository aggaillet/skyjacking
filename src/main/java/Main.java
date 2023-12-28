import algorithms.message.EMessageAlgorithm;
import algorithms.message.MessageAlgorithmManager;
import algorithms.position.IPositionAlgorithm;
import algorithms.position.implementations.AlgoPNaive;
import configuration.ConfigLoader;
import gpsutils.GpsEncoder;
import gpsutils.GpsPosition;
import gpsutils.SatelliteMessageInfo;
import message_controller.IMessageController;
import message_controller.SimulatorController;
import outputFeature.CommandLineOutput;
import outputFeature.IOutput;
import outputFeature.Logger;
import outputFeature.OutputData;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static gpsutils.GpsTimeUtil.getTowOfNow;

/**
 * @author Angelo G. Gaillet
 */
public class Main {
        static MessageAlgorithmManager messageAlgorithmManager;
        static IMessageController messageController = new SimulatorController();
        static Scheduler scheduler = new Scheduler(5, messageController);
        static List<IOutput> outputs;
        static GpsEncoder encoder = new GpsEncoder();
        static ConfigLoader configLoader = new ConfigLoader();
        static IPositionAlgorithm positionAlgorithm = new AlgoPNaive(); // TODO: temporary solution - remove hardcoding

        public static void main(String[] args){
                EMessageAlgorithm msgAlgoType = configLoader.getMsgAlgoType();
                messageAlgorithmManager = new MessageAlgorithmManager(msgAlgoType);
                outputs = new ArrayList<>();
                outputs.add(new CommandLineOutput(configLoader.getSelectedOutputs()));
                outputs.add(new Logger(configLoader.getDirectory(), configLoader.getSelectedOutputs()));
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(Main::run, 0, configLoader.getRefreshRate(), TimeUnit.NANOSECONDS);
        }

        private static void run(){
                GpsPosition currentPosition = messageController.requestPosition();
                GpsPosition spoofedPosition = positionAlgorithm.computeSpoofedPosition(currentPosition, configLoader.getDestination());
                List<SatelliteMessageInfo> satMsgInfo = messageAlgorithmManager.computeMessage(spoofedPosition);
                satMsgInfo.forEach(Main::createAndScheduleNavMsgs);
                log(currentPosition, spoofedPosition);
        }

        private static void createAndScheduleNavMsgs(SatelliteMessageInfo satMsgInfo){
                int delay = 2000; //TODO: implement (nanoseconds)
                double delayS = delay / 1000.;
                if (4 * delay < configLoader.getRefreshRate()){
                        throw new RuntimeException("Refresh rate is too fast. Minimum supported: " + 4 * delay + " ns");
                } else if (delayS < satMsgInfo.getDeltaT()) {
                        throw new RuntimeException("Unable to send messages in the past. Please increase transmission delay");
                }
                byte [] msg1 = encoder.createFirstSubframe(satMsgInfo.getEphemeris(), (int) (getTowOfNow() + delayS - satMsgInfo.getDeltaT()));
                byte [] msg2 = encoder.createSecondSubframe(satMsgInfo.getEphemeris(), (int) (getTowOfNow() + 2 * delayS - satMsgInfo.getDeltaT()));
                byte [] msg3 = encoder.createThirdSubframe(satMsgInfo.getEphemeris(), (int) (getTowOfNow() + 3 * delayS - satMsgInfo.getDeltaT()));
                byte [] msg4 = encoder.createFourthSubframe(satMsgInfo.getSupportData(), (int) (getTowOfNow() + 4 * delayS - satMsgInfo.getDeltaT()));
                scheduler.scheduleSend(msg1, OffsetDateTime.now().plusNanos(delay));
                scheduler.scheduleSend(msg2, OffsetDateTime.now().plusNanos(2 * delay));
                scheduler.scheduleSend(msg3, OffsetDateTime.now().plusNanos(3 * delay));
                scheduler.scheduleSend(msg4, OffsetDateTime.now().plusNanos(4 * delay));
        }

        private static void log(GpsPosition currentPosition, GpsPosition spoofedPosition){
                for (IOutput logger : outputs) {
                        logger.write(OutputData.RESULT_TRAJ, currentPosition.toString());
                        logger.write(OutputData.SPOOFED_TRAJ, spoofedPosition.toString());
                }
        }
}
