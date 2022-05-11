package com.kuro.trip_favo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

//NavigationライブラリのFragmentNavigatorクラスのnavigate()でFragmentは再生成されている
//だけど状態を保存したいからnavigate()を上書きして再生成しないようにしている
//FragmentNavigator: Fragmentのナビゲーター
//valueはnaviGraphのfragment名
@Navigator.Name("custom_fragment")
class CustomNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    //destination: 移動先の宛先?
    //args: ナビゲーションに使用する引数?
    //navOptions: ナビゲーション用の追加オプション
    //navigatorExtras: ナビゲーターに固有のエクストラ
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        //isStateSaved: 状態が保存されている時はtrueを返し状態を変更する操作をしない　意味不明けど問題フラグ
        //return null?
        if (manager.isStateSaved) {
            return null
        }

        //className: この宛先に関連付けされているフラグメントのクラス名 必須っぽい
        var className = destination.className
        Log.d("custom", className)
        //?
        if (className[0] == '.') {
            className = context.packageName + className
        }

        //宛先の一意のIDでandroidリソースシステムによって生成された
        val tag = destination.id.toString()
        //FragmentManagerに関連付けられているフラグメントで一連の編集操作を開始する関数
        val transaction = manager.beginTransaction()

        //FragmentManagerの現在アクティブなプライマリナビゲーションフラグメントを返す
        val currentFragment = manager.primaryNavigationFragment
        //アクティブなプライマリナビゲーションがあるなら、既存のフラグメントを非表示にする?
        //同時に２つ以上のフラグメントを表示しないようにしている?
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        //findFragmentByTag: タグによってfragmentを識別し検索する
        //先に現在のフラグメントを検索する→ない場合はバックスタックの全Fragmentが検索される
        var fragment = manager.findFragmentByTag(tag)
        //nullなら指定されたFragmentをインスタンス化して指定したFragmentを追加する
        //instantiateFragment: フラグメントをインスタンス化する
        if (fragment == null) {
            fragment = instantiateFragment(context, manager, className, args)
            //フラグメントを追加する
            transaction.add(containerId, fragment, tag)
        }
        //?
        fragment.arguments = args

        transaction.show(fragment)
        //現在のアクティブなFragmentとして設定
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.commit()

        //?
        return destination

    }
}

class CustomNavHostFragment : NavHostFragment() {
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return CustomNavigator(requireContext(), childFragmentManager, id)
    }
}
