package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratedColorCode implements ColorCodeInterface {

    private final List<String> generatedColorCode;

    public GeneratedColorCode() {
        this.generatedColorCode = generateColorCode();
        System.out.println(" (Test: Generated color code " + get() + ")");
    }

    public List<String> generateColorCode() {

        List<String> generatedColorCode = new ArrayList<>();
        for (int i = 0; i < numberColors; i++) {
            generatedColorCode.add(pickColor());
        }

        return generatedColorCode;
    }

    private String pickColor() {

        Random r = new Random();
        int selectedColorIndex = r.nextInt(availableColors.size());
        return availableColors.get(selectedColorIndex);
    }

    @Override
    public List<String> get() {

        return generatedColorCode;
    }
}
