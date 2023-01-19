package antigravity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LikedDto {
	
	private Long LikedNo;
	private Long productId;
	private Long userId;
	
	

}
