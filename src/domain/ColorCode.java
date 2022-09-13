package domain;

import java.util.Arrays;
import java.util.List;

public interface ColorCode {

    int numberColors = 4;
    List<String> availableColors = Arrays.asList("red","green","blue","brown","yellow","orange");

    List<String> get();
    String getColor(int index);
}
