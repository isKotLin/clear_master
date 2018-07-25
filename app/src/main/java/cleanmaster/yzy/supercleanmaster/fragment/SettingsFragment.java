package cleanmaster.yzy.supercleanmaster.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.yzy.supercleanmaster.R;
import cleanmaster.yzy.supercleanmaster.base.FragmentContainerActivity;
import cleanmaster.yzy.supercleanmaster.utils.AppUtil;
import cleanmaster.yzy.supercleanmaster.utils.T;
import cleanmaster.yzy.supercleanmaster.utils.Utils;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    public static void launch(Activity from) {
        FragmentContainerActivity.launch(from, SettingsFragment.class, null);
    }

    private Preference createShortCut;
    private Preference pVersion;
    private Preference pVersionDetail;
    private Preference pGithub;// Github
    private Preference pGrade;// Github
    private Preference pShare;// Github
    private Preference pAbout;// Github
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPreferencesFromResource(R.xml.ui_settings);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        getActivity().getActionBar().setTitle(R.string.title_settings);

//        createShortCut = findPreference("createShortCut");
//        createShortCut.setOnPreferenceClickListener(this);
//        pVersion = findPreference("pVersion");
//        pVersion.setOnPreferenceClickListener(this);
        pVersionDetail = findPreference("pVersionDetail");

        String currentversion = getResources().getString(R.string.currentversion);
        pVersionDetail.setSummary(currentversion + AppUtil.getVersion(getActivity()));
        pVersionDetail.setOnPreferenceClickListener(this);

//        pGithub = findPreference("pGithub");
//        pGithub.setOnPreferenceClickListener(this);
//        pGrade = findPreference("pGrade");
//        pGrade.setOnPreferenceClickListener(this);
//        pShare = findPreference("pShare");
//        pShare.setOnPreferenceClickListener(this);
//        pAbout = findPreference("pAbout");
//        pAbout.setOnPreferenceClickListener(this);
        initData();
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
//        if ("createShortCut".equals(preference.getKey())) {
//            createShortCut();
//        } else if ("pVersion".equals(preference.getKey())) {
//            UmengUpdateAgent.forceUpdate(getActivity());
//            UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
//                @Override
//                public void onUpdateReturned(int i, UpdateResponse updateResponse) {
//                    if (i != 0) {
//                        T.showLong(getActivity(), "当前版本为最新版本！");
//                    }
//
//                }
//            });
//        } else if ("pVersionDetail".equals(preference.getKey())) {
//            VersionFragment.launch(getActivity());
//        } else if ("pGithub".equals(preference.getKey())) {
//            Utils.launchBrowser(getActivity(), "https://github.com/joyoyao/superCleanMaster");
//        }else if ("pGrade".equals(preference.getKey())) {
//            startMarket();
//        }else if ("pShare".equals(preference.getKey())) {
//                shareMyApp();
//        }
//        else if ("pAbout".equals(preference.getKey())) {
//            getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
//        }
        return false;
    }

    private void shareMyApp() {

        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
        mController.setShareContent("一键清理");
        mController.openShare(getActivity(), false);

    }

    private void initData() {
        String appID = "wxa263da737a20300e";
        String appSecret = "381a2fab6466410c674afaa40c77c953";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(),appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();



    }


    private void createShortCut() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "一键加速");
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.short_cut_icon));
        Intent i = new Intent();
        i.setAction("com.yzy.shortcut");
        i.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
        getActivity().sendBroadcast(intent);
        T.showLong(getActivity(), "“一键加速”快捷图标已创建");

    }


    public  void startMarket() {
        Uri uri = Uri.parse(String.format("market://details?id=%s", AppUtil.getPackageInfo(getActivity()).packageName));
        if (Utils.isIntentSafe(getActivity(), uri)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
        }
        // 没有安装市场
        else {
            T.showLong(getActivity(),"无法打开应用市场");

        }
    }
}
