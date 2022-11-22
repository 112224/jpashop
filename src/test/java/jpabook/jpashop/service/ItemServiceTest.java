package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //given
        Album item = new Album();
        item.setName("사건의 지평선");
        item.setPrice(18000);
        item.setStockQuantity(100000);
        item.setArtist("윤하");

        //when
        Long savedId = itemService.saveItem(item);

        //then
        assertThat(item).isEqualTo(itemRepository.findOne(savedId));
    }

    @Test
    public void 재고더하기() throws Exception {
        //given
        int initialQuantity = 100000;
        Album item = new Album();
        item.setName("사건의 지평선");
        item.setPrice(18000);
        item.setStockQuantity(initialQuantity);
        item.setArtist("윤하");

        //when
        int diff = 30000;
        item.addStock(diff);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(initialQuantity + diff);
    }

    @Test
    public void 재고빼기_가능한_값() throws Exception {
        //given
        int initialQuantity = 100000;
        Album item = new Album();
        item.setName("사건의 지평선");
        item.setPrice(18000);
        item.setStockQuantity(initialQuantity);
        item.setArtist("윤하");

        //when
        int diff = 50000;
        item.removeStock(diff);
        assertThat(item.getStockQuantity()).isEqualTo(initialQuantity - diff);
        //then
        item.removeStock(diff);
        assertThat(item.getStockQuantity()).isEqualTo(initialQuantity - 2 * diff);
    }

    @Test
    public void 재고빼기_재고보다_많이() throws Exception {
        //given
        int initialQuantity = 100000;
        Album item = new Album();
        item.setName("사건의 지평선");
        item.setPrice(18000);
        item.setStockQuantity(initialQuantity);
        item.setArtist("윤하");
        //when
        int diff = initialQuantity + 1;
        //then
        assertThrows(NotEnoughStockException.class, () ->
                item.removeStock(diff)
        );
    }

}