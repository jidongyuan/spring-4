package com.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016-12-12.
 */

@Service
public class BookShopServiceImpl implements BookShopService {

    @Autowired
    private BookShopDao bookShopDao;



    //�������ע��
    //ʹ��propagationָ������Ĵ�����Ϊ������ǰ�����񷽷�����һ�����񷽷�����ʱ�����ʹ������Ĭ��ȡֵΪREQUIRED,��ʹ�õ��÷���������
    // REQUIRES_NEW �����Լ������񣬵��õ����񷽷������񱻹���
    //2.ʹ�������isolationָ������ĸ��뼶����õ�ȡֵ��READ_COMMITTED
    //3.Ĭ������£�spring������ʽ��������е�����ʱ�쳣���лع���Ҳ����ͨ����Ӧ�����Խ�������,
    //ͨ�������ȡĬ��ֵ����
    //4.ʹ��readOnlyָ�������Ƿ�ֻ������ʾ�������ֻ��ȡ���ݣ�������������,
    //�������԰������ݿ������Ż������������һ��ֻ��ȡ���ݿ�ֵ�ķ�����Ӧ����readOnly = true
    //5.ʹ��timeoutָ��ǿ�ƻع�֮ǰ�����ռ�õ�ʱ��
    /*@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED,noRollbackFor = {UserAccountException.class})*/
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED,readOnly = false
            ,timeout = 3)
    @Override
    public void purchase(String username, String isbn) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*1.��ȡ��ĵ���*/
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        /*2.������Ŀ��*/
        bookShopDao.updateBookStock(isbn);
        /*3.�����û����*/
        bookShopDao.updateUserAccount(username,price);
    }
}
