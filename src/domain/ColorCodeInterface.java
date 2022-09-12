package domain;

import java.util.Arrays;
import java.util.List;

public interface ColorCodeInterface {

    int numberColors = 4;
    List<String> availableColors = Arrays.asList("red","green","blue","brown","yellow","orange");

    List<String> getColorCode();
}
