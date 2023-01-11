package antigravity.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    String result;
    int statusCode;

}
