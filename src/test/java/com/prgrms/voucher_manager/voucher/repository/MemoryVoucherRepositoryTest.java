package com.prgrms.voucher_manager.voucher.repository;

import com.prgrms.voucher_manager.exception.EmptyRepositoryException;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.Voucher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
class MemoryVoucherRepositoryTest {

    static VoucherRepository repository;
    static Map<UUID, Voucher> storage;
    static FixedAmountVoucher fix;
    static PercentDiscountVoucher percent;

    @BeforeAll
    static void setup() {
        storage = new HashMap<>();
        repository = new MemoryVoucherRepository();
        fix = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        percent = new PercentDiscountVoucher(UUID.randomUUID(), 20);
    }

    @Test
    @DisplayName("바우처 생성 테스트")
    void testInsert() {
        repository.insert(fix);
        List<Voucher> all = repository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all.get(0), samePropertyValuesAs(fix));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void testFindAll() {
        assertThrows(EmptyRepositoryException.class, () -> repository.findAll());
        repository.insert(percent);
        List<Voucher> all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("아이디를 통해 바우처 조회 테스트")
    void testFindById() {
        repository.insert(percent);
        Optional<Voucher> find = repository.findById(percent.getVoucherId());
        assertThat(find.isEmpty(), is(false));
        assertThat(find.get(), samePropertyValuesAs(percent));
    }

    @Test
    @DisplayName("타입을 통해 바우처 조회 테스트")
    void testFindByType() {
        repository.insert(percent);
        repository.insert(fix);
        List<Voucher> find = repository.findByType("fix");
        assertThat(find.isEmpty(), is(false));
        assertThat(find, hasSize(1));
        assertThat(find.get(0), samePropertyValuesAs(fix));
    }

    @Test
    @DisplayName("value 변경 테스트")
    void testChangeValue() {
        fix.changeValue(300L);
        percent.changeValue(50L);
        FixedAmountVoucher test = new FixedAmountVoucher(fix.getVoucherId(), 300L);
        PercentDiscountVoucher test2 = new PercentDiscountVoucher(percent.getVoucherId(), 50L);
        assertThat(test, samePropertyValuesAs(fix));
        assertThat(test2, samePropertyValuesAs(percent));
    }

    @Test
    @DisplayName("바우처 정보 수정")
    void testUpdate() {
        fix.changeValue(4000L);
        repository.update(fix);
        List<Voucher> all = repository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all.get(0), samePropertyValuesAs(fix));
    }

    @Test
    @DisplayName("바우처 삭제 테스트")
    void testDelete() {
        repository.insert(percent);
        repository.insert(fix);
        repository.delete(fix);

        Optional<Voucher> find = repository.findById(fix.getVoucherId());
        assertThat(find, samePropertyValuesAs(Optional.empty()));

        List<Voucher> all = repository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all.get(0), samePropertyValuesAs(percent));
    }


}