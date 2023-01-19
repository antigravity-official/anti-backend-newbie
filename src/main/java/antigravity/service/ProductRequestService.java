package antigravity.service;

import antigravity.constant.ErrorCode;
import antigravity.entity.Basket;
import antigravity.entity.Product;
import antigravity.entity.ProductInfo;
import antigravity.exception.GeneralException;
import antigravity.payload.DataConverter;
import antigravity.payload.ProductResponse;
import antigravity.repository.BasketRepository;
import antigravity.repository.ProductInfoRepository;
import antigravity.repository.ProductRepository;
import antigravity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductRequestService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    // TODO : user 별로 api 받기
    public List<ProductResponse> getProducts(
            Boolean liked,
            Integer page,
            Integer size
    )
    {
        try {
            ProductResponse[] productResponses = new ProductResponse[0];
            Stream<Long> idList = basketRepository.findAllByUserId(1L)
                    .stream()
                    .map(x -> x.getProduct_Id());

            Long[] list = idList.toArray(Long[]::new);
            if (liked.equals(true)) {

                Product[] productList = new Product[list.length];
                ProductInfo[] productInfoList = new ProductInfo[list.length];
                productResponses = new ProductResponse[list.length];
                for (int i = 0; i < list.length; i++) {
                    productList[i] = productRepository.findById(list[i]).get();
                    productInfoList[i] = productInfoRepository.findById(list[i]).get();
                    productResponses[i] = DataConverter.fromProductAndBasketAndProductInfo(
                            productList[i],
                            basketRepository.findById(list[i]).get(),
                            productInfoList[i]
                    );
                }
            } else {
                List<Product> productList = productRepository.findAll();
                Long[] notIdList = new Long[productList.size() - list.length];
                Long[] productListArr = new Long[productList.size()];
                for (int i = 0; i < productList.size(); i++) {
                    productListArr[i] = productList.get(i).getId();
                }
                List<Long> list1 = new ArrayList(Arrays.asList(productListArr));
                List<Long> list2 = new ArrayList(Arrays.asList(list));

                list1.addAll(list2);
                Long[] dest = list1.toArray(new Long[0]);
                Map<Long, Integer> map = new HashMap<>();
                for (Long x : dest) {
                    map.put(x, map.getOrDefault(x, 0) + 1);
                }
                int j = 0;
                for (Long x : map.keySet()) {
                    if (map.get(x) == 1)
                        notIdList[j++] = x;
                }

                Product[] productList2 = new Product[notIdList.length];
                ProductInfo[] productInfoList2 = new ProductInfo[notIdList.length];
                productResponses = new ProductResponse[notIdList.length];
                for (int i = 0; i < notIdList.length; i++) {
                    productList2[i] = productRepository.findById(notIdList[i]).get();
                    productInfoList2[i] = productInfoRepository.findById(notIdList[i]).get();
                    productResponses[i] = DataConverter.fromProductAndBasketAndProductInfo(
                            productList2[i],
                            productInfoList2[i]
                    );
                }
            }

            if (page > productResponses.length / size && page != 0)
                page = productResponses.length / size;
            else if (page == 0)
                page = 1;
            if (size > productResponses.length) {
                size = productResponses.length;
                page = 1;
            }
            // TODO : page, size 처리
            List<ProductResponse> result = Arrays.asList(productResponses).subList((page - 1) * size , (page - 1) * size + size);

            return result;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

}
