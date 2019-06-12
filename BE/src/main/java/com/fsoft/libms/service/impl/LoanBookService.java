package com.fsoft.libms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.libms.service.AbstractService;
import com.fsoft.libms.service.ILoanBookService;
@Service
@Transactional(readOnly = true)
public class LoanBookService extends AbstractService implements ILoanBookService {

}
