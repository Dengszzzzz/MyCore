package com.dengzh.sample.module.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengzh.sample.R;
import com.dengzh.sample.bean.RealmUserBean;
import com.dengzh.sample.module.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by dengzh on 2017/11/4.
 * Realm
 * 开启事务：
 * mRealm.executeTransactionAsync(...); 开启子线程执行事务，大数据考虑用此，可以加入监听
 * mRealm.executeTransaction(...);      在UI线程执行事务
 *
 * 增：
 * 1）mRealm.createObject(RealmUserBean.class)
 * 2）mRealm.copyToRealm(userBean2);
 * 3) mRealm.copyToRealmOrUpdate(userBean2);   有主键的时候才用这个，不然会抛异常
 *
 * 删：
 * 1) userList.get(0).deleteFromRealm();
 * 2) userList.deleteFromRealm(0);
 * 5）results.deleteAllFromRealm();
 * 参考：
 * http://www.jianshu.com/p/37af717761cc
 */

public class RelamActivity extends BaseActivity {

    @BindView(R.id.contentTv)
    TextView contentTv;

    private int id;
    private Realm mRealm;

    @Override
    protected int getLayoutId() {
        return R.layout.ac_realm;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mRealm = Realm.getDefaultInstance();
        queryRealm();
    }

    @OnClick({R.id.addBt, R.id.delBt, R.id.updateBt, R.id.findBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addBt:
                addRealm();
                break;
            case R.id.delBt:
                delRealm();
                break;
            case R.id.updateBt:
                updateRealm();
                break;
            case R.id.findBt:
                queryRealm();
                break;
        }
    }

    public void addRealm(){
        //实现方法一：事务操作
        //新建一个对象，并进行存储
        mRealm.beginTransaction();
        RealmUserBean userBean = mRealm.createObject(RealmUserBean.class,++id);  //第二个参数primaryKey 类设置了，此处一定要写
        userBean.setName("dengzh"+id);
        userBean.setEmail("dengzh"+id+".com");
        userBean.setAge(10+id);
        mRealm.commitTransaction();
        //类型二：复制一个对象到Realm数据库
        RealmUserBean userBean1 = new RealmUserBean();
        userBean1.setId(++id);
        userBean1.setName("dengzh"+id);
        userBean1.setEmail("dengzh"+id+".com");
        userBean1.setAge(10+id);
        mRealm.beginTransaction();
        mRealm.copyToRealm(userBean1);
        mRealm.commitTransaction();

        //实现方法二：使用事务块,可以理解把beginTransaction() -- commitTransaction()之间的逻辑放到execute方法中
        final RealmUserBean userBean2 = new RealmUserBean();
        userBean2.setId(++id);
        userBean2.setName("dengzh"+id);
        userBean2.setEmail("dengzh"+id+".com");
        userBean2.setAge(10+id);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                /**
                 * 使用copyToRealmOrUpdate或copyToRealm方法插入数据
                 * 当Model中存在主键的时候，推荐使用copyToRealmOrUpdate方法插入数据。
                 * 如果对象存在，就更新该对象；反之，它会创建一个新的对象。
                 * 若该Model没有主键，使用copyToRealm方法，否则将抛出异常
                 * */
                //realm.copyToRealm(userBean2);
                realm.copyToRealmOrUpdate(userBean2);
                contentTv.setText("成功添加" + userBean2.toString());
            }
        });
    }


    public void queryRealm(){
        RealmResults<RealmUserBean> users = mRealm
                .where(RealmUserBean.class)
                .findAll()
                .sort("id",Sort.ASCENDING);


        String str = "";
        for (int i = 0; i < users.size(); i++) {
            RealmUserBean user = users.get( i);
            str = str + user.toString()+"\n";
        }
        contentTv.setText("查询所有数据：\n" + str);

        //PrimaryKey id
        id = users.size() == 0? 1:users.get(users.size()-1).getId();
    }

    /**
     * 更新数据，先找到对象
     */
    public void updateRealm(){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //先查找后得到User对象
                RealmUserBean userBean = mRealm.where(RealmUserBean.class).equalTo("id",5).findFirst();
                id++;
                if(userBean==null){
                    userBean = mRealm.createObject(RealmUserBean.class,id);
                }
                userBean.setName("dengzh"+id);
                userBean.setEmail("dengzh"+id+".com");
                userBean.setAge(10+id);
            }
        });
    }

    /**
     * 删除数据，先找到对象
     */
    public void delRealm(){
        final RealmResults<RealmUserBean> userList = mRealm.where(RealmUserBean.class).findAll().sort("id",Sort.ASCENDING);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(userList.size()>0){
                    userList.get(0).deleteFromRealm();
                    // userList.deleteFromRealm(0);
                    // userList.deleteFirstFromRealm();
                    // userList.deleteLastFromRealm();
                    // userList.deleteAllFromRealm();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
