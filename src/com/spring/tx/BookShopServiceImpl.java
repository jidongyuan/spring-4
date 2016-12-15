package com.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void purchase(String username, String isbn) {
        /*1.��ȡ��ĵ���*/
        int price = bookShopDao.findBookPriceByIsbn(isbn);
        /*2.������Ŀ��*/
        bookShopDao.updateBookStock(isbn);
        /*3.�����û����*/
        bookShopDao.updateUserAccount(username,price);
    }
}
