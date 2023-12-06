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

import java.util.ArrayList;
import java.util.List;

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
                while(true){
                        run();
                }
        }

        private static void run(){
                GpsPosition currentPosition = messageController.requestPosition();
                GpsPosition spoofedPosition = positionAlgorithm.computeSpoofedPosition(currentPosition, configLoader.getDestination());
                List<SatelliteMessageInfo> satMsgInfo = messageAlgorithmManager.computeMessage(spoofedPosition);
                satMsgInfo.forEach(Main::createAndScheduleNavMsgs);
                log(currentPosition, spoofedPosition);
        }

        private static void createAndScheduleNavMsgs(SatelliteMessageInfo satMsgInfo){
                //TODO: implement
        }

        private static void log(GpsPosition currentPosition, GpsPosition spoofedPosition){
                for (IOutput logger : outputs) {
                        logger.write(OutputData.RESULT_TRAJ, currentPosition.toString());
                        logger.write(OutputData.SPOOFED_TRAJ, spoofedPosition.toString());
                }
        }
}
