package org.prgrms.kdt.io;

import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.voucher.VoucherData;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 6:09 오후
 */
public interface Input {

    CommandType inputCommand();

    VoucherData inputVoucher();

}
