package com.szyd.moduleuhf_50;

import android.util.Log;
import android.widget.Toast;

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
 */
public class UHFDemo extends UZModule {

    public static UZModuleContext mModuleContext;

    public UHFDemo(UZWebView webView) {
        super(webView);
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
        Map<String, String> map = uhf.readUHFData(this.getContext(), startIndex, length, memIndex);
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
     * writeUHFData:写入到电子标签
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
        Map<String, String> map = uhf.writeUHFData(this.getContext(), memIndex, startIndex, length, str);

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
