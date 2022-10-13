package com.U8.moduleuhf_u8;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fntech.activity.SetAndSaveActivity;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 该类映射至Javascript中moduleDemo对象
 * <p>
 * var module = api.require('moduleUHF_I6310');
 * module.xxx();
 * Created by yqf on 2020/5/20.
 * UHFReadWrite.java类被加密了，此模块无法使用
 */
public class UHFDemo extends UZModule {
    public static UZModuleContext mModuleContext;

    public UHFDemo(UZWebView webView) {
        super(webView);
    }

    //jsmethod_方法名，可被api调用。这里的方法名就是在html中的方法可传递msg
    //tips:引擎默认在UI线程中操作该函数,不能作耗时操作
    public void jsmethod_test(UZModuleContext moduleContext) {
        mModuleContext = moduleContext;
        String msg = moduleContext.optString("msg");
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * <strong>函数</strong><br><br>
     * 该函数映射至Javascript中moduleDemo对象的startActivity函数<br><br>
     * <strong>JS Example：</strong><br>
     * modulePdf.startActivity(argument);
     *
     * @param moduleContext (Required)
     */
    public void jsmethod_startActivity(UZModuleContext moduleContext) {
        mModuleContext = moduleContext;
        Log.w("onCreate", "自定义模块接收的参数：" + moduleContext.optString("name"));

        Intent intent = new Intent(getContext(), SetAndSaveActivity.class);
        intent.putExtra("name", moduleContext.optString("name"));
        startActivity(intent);
    }

    /**
     * readUHF:读取UHF标签的信息，先初始化，再根据传参读取
     *
     * @param moduleContext
     */
    public void jsmethod_readUHF(UZModuleContext moduleContext) throws Exception {
        mModuleContext = moduleContext;
        UHFReadWrite uhf = new UHFReadWrite();
        uhf.initUHFModule(this.getContext());
        Map<String, String> map = uhf.readUHF();
        uhf.recyleUHFModule();
        String flag = map.get("flag");
        String epc = map.get("epc");
        String info = map.get("info");
        Toast.makeText(mContext, "flag:" + flag + "\nepc:" + epc + "\ninfo:" + info, Toast.LENGTH_SHORT).show();
        // --回调结果
        JSONObject ret = new JSONObject();
        try {
            ret.put("flag", flag);
            ret.put("epc", epc);
            ret.put("info", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        moduleContext.success(ret, true);
    }


    /**
     * readUHFData:读取电子标签
     *
     * @param moduleContext
     */
    public void jsmethod_readUHFData(UZModuleContext moduleContext) throws Exception {
        mModuleContext = moduleContext;
        // 接收到参：起始位置、长度、存储区、写入的信息
        String startStr = moduleContext.optString("startIndex");
        String lengthStr = moduleContext.optString("length");
        String memStr = moduleContext.optString("memType"); // 读哪种类型的数据，分RESEVER/EPC/TID/USER
        int startIndex = startStr == "" ? 0 : Integer.parseInt(startStr); // 默认起始位置0
        int length = lengthStr == "" ? 6 : Integer.parseInt(lengthStr); // 默认长度为6
        int memIndex = memStr == "" ? 3 : Integer.parseInt(memStr); // 默认类型为3-USER存储区
        UHFReadWrite uhf = new UHFReadWrite();
        uhf.initUHFModule(this.getContext());
        Map<String, String> map = uhf.readUHFData(startIndex, length, memIndex);
        uhf.recyleUHFModule();
        String flag = map.get("flag");
        String epc = map.get("epc");
        String info = map.get("info");
        Toast.makeText(mContext, "flag:" + flag + "\nepc:" + epc + "\ninfo:" + info, Toast.LENGTH_SHORT).show();
        // --回调结果
        JSONObject ret = new JSONObject();
        try {
            ret.put("flag", flag);
            ret.put("epc", epc);
            ret.put("info", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        moduleContext.success(ret, true);
    }

    /**
     * writeUHF:写入到电子标签
     *
     * @param moduleContext
     */
    public void jsmethod_writeUHFData(UZModuleContext moduleContext) throws Exception {
        mModuleContext = moduleContext;
        // 接收到参：起始位置、长度、存储区、写入的信息
        String str = moduleContext.optString("str"); // 写入的数据
        String startStr = moduleContext.optString("startIndex");
        String lengthStr = moduleContext.optString("length");
        String memStr = moduleContext.optString("memType"); // 读哪种类型的数据，分RESEVER/EPC/TID/USER
        int startIndex = startStr == "" ? 0 : Integer.parseInt(startStr); // 默认起始位置0
        int length = lengthStr == "" ? 6 : Integer.parseInt(lengthStr); // 默认长度为6
        int memIndex = memStr == "" ? 3 : Integer.parseInt(memStr); // 默认类型为3-USER存储区
        UHFReadWrite uhf = new UHFReadWrite();
        uhf.initUHFModule(this.getContext());
        Map<String, String> map = uhf.writeUHFData(startIndex, length, memIndex, str);
        uhf.recyleUHFModule();
        String flag = map.get("flag");
        String epc = map.get("epc");
        String info = map.get("info");
        Toast.makeText(mContext, "flag:" + flag + "\nepc:" + epc + "\ninfo:" + info, Toast.LENGTH_SHORT).show();

        // --回调结果
        JSONObject ret = new JSONObject();
        try {
            ret.put("flag", flag);
            ret.put("epc", epc);
            ret.put("info", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        moduleContext.success(ret, true);
    }
}
