package antigravity.payload;

import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResponseRowMapper implements RowMapper<ProductResponse> {
    public ProductResponse mapRow(ResultSet rs, int rowNum) throws SQLException{
        ProductResponse productResponse = new ProductResponse();

        productResponse.setSku(rs.getString("sku"));
        productResponse.setName(rs.getString("name"));
        productResponse.setViewed(rs.getInt("viewed"));

        return productResponse;
    }
}
