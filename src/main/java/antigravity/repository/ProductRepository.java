package antigravity.repository;

import antigravity.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ProductRepository {
    private final EntityManager em;

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll(Integer offset, Integer limit) {
        String sql = " SELECT p FROM Product p ";

        TypedQuery<Product> productTypedQuery = em.createQuery(sql, Product.class);
        if (offset != null && limit != null) {
            productTypedQuery
                    .setFirstResult(offset)
                    .setMaxResults(limit);
        }
        return productTypedQuery.getResultList();
    }

    public List<Product> findLikedProduct(Long userId, Integer offset, Integer limit) {
        String sql = " SELECT p FROM Product p " +
                " LEFT JOIN LikedProduct lp ON lp.product.id = p.id " +
                " WHERE " +
                " lp.customer.id = :userId ";


        TypedQuery<Product> query = em.createQuery(sql, Product.class)
                .setParameter("userId", userId);

        if (offset != null && limit != null) {
            query.setFirstResult(offset)
                    .setMaxResults(limit);
        }
        return query.getResultList();
    }


    public List<Product> findNotLikedProduct(Long userId, Integer offset, Integer limit) {
        String sql =
                "SELECT p FROM \n" +
                        "              Product p \n" +
                        "    WHERE \n" +
                        "      p.id NOT IN ( \n" +
                        "          SELECT lp.product.id \n" +
                        "          FROM\n" +
                        "               LikedProduct lp \n" +
                        "          WHERE lp.customer.id = :userId)\n";

        TypedQuery<Product> query = em.createQuery(sql, Product.class)
                .setParameter("userId", userId);

        if (offset != null && limit != null) {
            query.setFirstResult(offset)
                    .setMaxResults(limit);
        }

        return query.getResultList();
    }


}
