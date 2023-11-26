package outputFeature;

/* */
public class CommandLineInput implements IOutput{

    private String[] dataToPrint;

    public CommandLineInput(String[] dataToPrint){
        this.dataToPrint = dataToPrint;
    }

    @Override
    public void write() {

    }
}
