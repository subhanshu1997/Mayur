package com.cg.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.cg.bean.Account;
import com.cg.exception.InsufficientBalanceException;


public class AccountDAOImpl implements AccountDAO {
	
//		
//		String url;
//		String user;
//		String pass;
//		PreparedStatement insertSt=null;
//		PreparedStatement updateSt=null;
//		PreparedStatement deleteSt=null;
//		PreparedStatement selectSt=null;
//		Connection con;
	
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA-PU");
//		EntityManager em = emf.createEntityManager();
	EntityManagerFactory emf=Persistence.createEntityManagerFactory("JPA-PU");
	EntityManager em=emf.createEntityManager();
		public AccountDAOImpl() {
		
		}
		
		
		
		
	@Override
	public boolean addAccount(Account ob) {
		// TODO Auto-generated method stub
	
		try {
			em.getTransaction().begin();
			em.persist(ob);
			em.getTransaction().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	@Override
	public boolean updateAccount(Account ob) {
		// TODO Auto-generated method stub

		try {
			em.getTransaction().begin();
			em.merge(ob);
			//em.persist(ob);
			em.getTransaction().commit();
		}
		catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
		return true;
	}

	@Override
	public boolean deleteAccount(Account ob) {
		// TODO Auto-generated method stub
		
		try {
			em.getTransaction().begin();
			em.remove(ob);
			//em.persist(ob);
			em.getTransaction().commit();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public Account findAccount(Long mo) {
		// TODO Auto-generated method stub
		Account acc;
		try {
			acc=em.find(Account.class, mo);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return acc;
	}


	@Override
	public boolean transferMoney(Account from, Account to, Double amount) throws InsufficientBalanceException {
		// TODO Auto-generated method stub
		Double bal1=from.getBalance();
		Double bal2=to.getBalance();
			if(bal1-amount<1000)
				throw new InsufficientBalanceException("Balance Below 1000 so Transaction Invalid ",bal1);
			else
			{
				bal1=bal1-amount;
				bal2=bal2+amount;
				from.setBalance(bal1);
				to.setBalance(bal2);
				em.getTransaction().begin();
				em.persist(from);
				em.persist(to);
				em.getTransaction().commit();
			}
			
		
		return true;
	}


	@Override
	public List<Account> getAllAccount() {
		try {
			Query query=em.createQuery("select m from Account m");
			List<Account> list = query.getResultList();
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
}

	@Override
	public boolean deposit(Account ac, Double money) {
		
		try {
				ac.setBalance(ac.getBalance()+money);
				em.getTransaction().begin();
				em.persist(ac);
				em.getTransaction().commit();
			}
			
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean withdraw(Account ac, Double money) throws InsufficientBalanceException {
		
			
			if((ac.getBalance()-money)<1000)
				throw new InsufficientBalanceException("Balance Below 1000 so Transaction Invalid ",ac.getBalance());
			else
			{
			ac.setBalance(ac.getBalance()-money);
			em.getTransaction().begin();
			em.persist(ac);
			em.getTransaction().commit();
			}
	
	return true;
	}



}
