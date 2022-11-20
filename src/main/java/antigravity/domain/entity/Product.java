package antigravity.domain.entity;

import antigravity.util.BaseTimeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "product_id")
    private Long id;
    @Column
    private String sku;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private int view;
    @Column
    private int quantity;
    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<Want> wantList = new ArrayList<>();

    @Builder
    public Product(String sku, String name, BigDecimal price, int quantity){
        this.sku=sku;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    @PrePersist
    public void onPrePersist(){
        this.updatedAt = LocalDateTime.now();
    }
    public void addTotalView(){
        this.view++;
    }
}
