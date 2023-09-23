package gmbs.view.input;

import java.util.Scanner;

public class InputConsoleImpl implements InputConsole {

    private static final String NAME_DELIMITER = ",";
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String[] readNames() {
        return SCANNER.nextLine().split(NAME_DELIMITER);
    }

    @Override
    public String readBettingAmount() {
        return SCANNER.nextLine();
    }

    @Override
    public String readPossibilityOfDraw() {
        return SCANNER.nextLine();
    }
}
