package antigravity.service;

import antigravity.constant.ErrorCode;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductResponseService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private ProductResponse[] trueProductResponses = new ProductResponse[0];
    private ProductResponse[] falseProductResponses = new ProductResponse[0];

    public List<ProductResponse> getProducts(
            Boolean liked,
            Integer page,
            Integer size,
            Integer userId
    )
    {
        if (userRepository.existsById(userId.longValue()) == false)
            throw new GeneralException(ErrorCode.NOT_FOUND);
        // TODO : 시간 수정
        try {
            List<ProductResponse> result = new LinkedList<>();
            Stream<Long> idList = basketRepository.findAllByUserId(userId.longValue())
                    .stream()
                    .map(x -> x.getProduct_Id());

            Long[] list = idList.toArray(Long[]::new);
            if (liked == null) {

                trueProductResponses = ifTrue(list);
                falseProductResponses = ifFalse(list);
                List<ProductResponse> list1 = new ArrayList(Arrays.asList(trueProductResponses));
                List<ProductResponse> list2 = new ArrayList(Arrays.asList(falseProductResponses));

                int len = trueProductResponses.length + falseProductResponses.length;
                list1.addAll(list2);
                if (page > len / size && page != 0)
                    page = len / size;
                else if (page == 0)
                    page = 1;
                if (size > len) {
                    size = len;
                    page = 1;
                }

                result = list1.subList((page - 1) * size , (page - 1) * size + size);
                return result;
            }

            if (liked.equals(true)) {

                trueProductResponses = ifTrue(list);
                if (page > trueProductResponses.length / size && page != 0)
                    page = trueProductResponses.length / size;
                else if (page == 0)
                    page = 1;
                if (size > trueProductResponses.length) {
                    size = trueProductResponses.length;
                    page = 1;
                }
                result = Arrays.asList(trueProductResponses).subList((page - 1) * size , (page - 1) * size + size);
            } else if (liked.equals(false)){
                falseProductResponses = ifFalse(list);
                if (page > falseProductResponses.length / size && page != 0)
                    page = falseProductResponses.length / size;
                else if (page == 0)
                    page = 1;
                if (size > falseProductResponses.length) {
                    size = falseProductResponses.length;
                    page = 1;
                }
                result = Arrays.asList(falseProductResponses).subList((page - 1) * size , (page - 1) * size + size);
            }

            return result;
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public ProductResponse[] ifTrue(Long[] list) {
        Product[] productList = new Product[list.length];
        ProductInfo[] productInfoList = new ProductInfo[list.length];
        trueProductResponses = new ProductResponse[list.length];
        for (int i = 0; i < list.length; i++) {
            productList[i] = productRepository.findById(list[i]).get();
            productInfoList[i] = productInfoRepository.findById(list[i]).get();
            trueProductResponses[i] = DataConverter.fromProductAndBasketAndProductInfo(
                    productList[i],
                    basketRepository.findById(list[i]).get(),
                    productInfoList[i]
            );
        }
        return trueProductResponses;
    }

    public ProductResponse[] ifFalse(Long[] list) {
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
        falseProductResponses = new ProductResponse[notIdList.length];
        for (int i = 0; i < notIdList.length; i++) {
            productList2[i] = productRepository.findById(notIdList[i]).get();
            productInfoList2[i] = productInfoRepository.findById(notIdList[i]).get();
            falseProductResponses[i] = DataConverter.fromProductAndBasketAndProductInfo(
                    productList2[i],
                    productInfoList2[i]
            );
        }
        return falseProductResponses;
    }

}
