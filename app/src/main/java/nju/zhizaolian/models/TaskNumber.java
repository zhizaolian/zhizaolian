package nju.zhizaolian.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by ColorfulCode on 2015/4/20.
 */
public class TaskNumber implements Serializable{

    private int taskNumber;         //总任务数

    private int marketManager2;   //市场主管待办数值
    private int verifyQuote;       //审核报价
    private int verifyAlter;       //审核变更专员申请

    private int marketManager;     //市场部待办数值
    private int modifyOrder; //修改询单
    private int mergeQuote; //合并报价
    private int confirmQuote; //报价商定
    private int modifyQuote; //修改报价
    private int confirmProduceOrder; //确认大货加工单并签订合同
    private int pushRest;//催尾款
    private int modifyProduceOrder; //修改大货合同


    private int marketSecretary;  //市场秘书待办数值
    private int allocateOrder; //分配订单

    private int designManager;     //设计部待办数值
                                       //验证设计
    private int uploadDegisn;       //样衣版型录入及生产
    private int typeSettingSlice;  //排版切片
    private int confirmCad;         //确认版型



    private int craftManager;       //工艺部待办数值
    private int computeDesignCost; //设计工艺验证
    private int craftSample;        //样衣工艺制作
    private int craft;              //大货工艺制作


    private int purchaseManager;  //采购部待办数值
    private int computePurchaseCost; //采购成本验证并核算
    private int purchaseSampleMaterial; //样衣采购
    private int purchaseMaterial; //大货面料采购验证
    private int buySweaterMaterial; //毛衣面料采购

    private int produceManager;   //生产部待办数值
    private int computeProduceCost;  //生产成本验证并核算
    private int produce; //大货批量生产

    private int SweaterMakeManager; //毛衣制作部待办数值
    private int confirmSweaterSampleAndCraft; //样衣确认和工艺制作
    private int sendSweater;                      //毛衣外发


    private int financeManager;   //财务部待办数值
    private int confirmSampleMoney;  //样衣费确认
    private int confirmDeposit; //首定金确认
    private int returnDeposit;  //退还定金
    private int confirmFinalPayment;  //尾款金确认

    private int logisticsManager;   //物流部待办数值
    private int receiveSample; //样衣收取
    private int sendSample; //样衣发货
    private int warehouse;  //产品入库
    private int sendClothes; //产品发货

    private int qualityManager;    //质检部待办数值
    private int checkQuality; //质量检查

    //多余的几个变量
    private int signContract;
    private int verifyProduce;
    private int produceSample;
    private int confirmPurchase;
    private int verifyPurchase;
    private int warehouse_haoduoyi;
    private int confirmDesign;
    private int modifyDesign;

    public int getTaskNumber() {
        return taskNumber;
    }

    public int getMarketManager2() {
        return marketManager2;
    }

    public int getVerifyQuote() {
        return verifyQuote;
    }

    public int getVerifyAlter() {
        return verifyAlter;
    }

    public int getMarketManager() {
        return marketManager;
    }

    public int getModifyOrder() {
        return modifyOrder;
    }

    public int getMergeQuote() {
        return mergeQuote;
    }

    public int getConfirmQuote() {
        return confirmQuote;
    }

    public int getModifyQuote() {
        return modifyQuote;
    }

    public int getConfirmProduceOrder() {
        return confirmProduceOrder;
    }

    public int getPushRest() {
        return pushRest;
    }

    public int getModifyProduceOrder() {
        return modifyProduceOrder;
    }

    public int getMarketSecretary() {
        return marketSecretary;
    }

    public int getAllocateOrder() {
        return allocateOrder;
    }

    public int getDesignManager() {
        return designManager;
    }

    public int getUploadDegisn() {
        return uploadDegisn;
    }

    public int getTypeSettingSlice() {
        return typeSettingSlice;
    }

    public int getConfirmCad() {
        return confirmCad;
    }

    public int getCraftManager() {
        return craftManager;
    }

    public int getComputeDesignCost() {
        return computeDesignCost;
    }

    public int getCraftSample() {
        return craftSample;
    }

    public int getCraft() {
        return craft;
    }

    public int getPurchaseManager() {
        return purchaseManager;
    }

    public int getComputePurchaseCost() {
        return computePurchaseCost;
    }

    public int getPurchaseSampleMaterial() {
        return purchaseSampleMaterial;
    }

    public int getPurchaseMaterial() {
        return purchaseMaterial;
    }

    public int getBuySweaterMaterial() {
        return buySweaterMaterial;
    }

    public int getProduceManager() {
        return produceManager;
    }

    public int getComputeProduceCost() {
        return computeProduceCost;
    }

    public int getProduce() {
        return produce;
    }

    public int getSweaterMakeManager() {
        return SweaterMakeManager;
    }

    public int getConfirmSweaterSampleAndCraft() {
        return confirmSweaterSampleAndCraft;
    }

    public int getSendSweater() {
        return sendSweater;
    }

    public int getFinanceManager() {
        return financeManager;
    }

    public int getConfirmSampleMoney() {
        return confirmSampleMoney;
    }

    public int getConfirmDeposit() {
        return confirmDeposit;
    }

    public int getReturnDeposit() {
        return returnDeposit;
    }

    public int getConfirmFinalPayment() {
        return confirmFinalPayment;
    }

    public int getLogisticsManager() {
        return logisticsManager;
    }

    public int getReceiveSample() {
        return receiveSample;
    }

    public int getSendSample() {
        return sendSample;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public int getSendClothes() {
        return sendClothes;
    }

    public int getQualityManager() {
        return qualityManager;
    }

    public int getCheckQuality() {
        return checkQuality;
    }

    public int getSignContract() {
        return signContract;
    }

    public int getVerifyProduce() {
        return verifyProduce;
    }

    public int getProduceSample() {
        return produceSample;
    }

    public int getConfirmPurchase() {
        return confirmPurchase;
    }

    public int getVerifyPurchase() {
        return verifyPurchase;
    }

    public int getWarehouse_haoduoyi() {
        return warehouse_haoduoyi;
    }

    public int getConfirmDesign() {
        return confirmDesign;
    }

    public int getModifyDesign() {
        return modifyDesign;
    }

    public static TaskNumber fromJson(JSONObject jsonObject){
        TaskNumber taskNumber= new TaskNumber();
        try {
            taskNumber.taskNumber=jsonObject.getInt("taskNumber");
            taskNumber.marketManager=jsonObject.getInt("marketManager");
            taskNumber.marketSecretary=jsonObject.getInt("marketSecretary");
            taskNumber.designManager=jsonObject.getInt("designManager");
            taskNumber.purchaseManager=jsonObject.getInt("purchaseManager");
            taskNumber.produceManager=jsonObject.getInt("produceManager");
            taskNumber.financeManager=jsonObject.getInt("financeManager");
            taskNumber.logisticsManager=jsonObject.getInt("logisticsManager");
            taskNumber.qualityManager=jsonObject.getInt("qualityManager");
            taskNumber.SweaterMakeManager=jsonObject.getInt("SweaterMakeManager");
            taskNumber.confirmProduceOrder=jsonObject.getInt("confirmProduceOrder");
            taskNumber.confirmQuote=jsonObject.getInt("confirmQuote");
            taskNumber.mergeQuote=jsonObject.getInt("mergeQuote");
            taskNumber.modifyOrder=jsonObject.getInt("modifyOrder");
            taskNumber.modifyProduceOrder=jsonObject.getInt("modifyProduceOrder");
            taskNumber.modifyQuote=jsonObject.getInt("modifyQuote");
            taskNumber.pushRest=jsonObject.getInt("pushRest");
            taskNumber.signContract=jsonObject.getInt("signContract");
            taskNumber.confirmSweaterSampleAndCraft=jsonObject.getInt("confirmSweaterSampleAndCraft");
            taskNumber.craft=jsonObject.getInt("craft");
            taskNumber.typeSettingSlice=jsonObject.getInt("typeSettingSlice");
            taskNumber.sendSample=jsonObject.getInt("sendSample");
            taskNumber.computeDesignCost=jsonObject.getInt("computeDesignCost");
            taskNumber.verifyProduce=jsonObject.getInt("verifyProduce");
            taskNumber.purchaseMaterial=jsonObject.getInt("purchaseMaterial");
            taskNumber.produceSample=jsonObject.getInt("produceSample");
            taskNumber.purchaseSampleMaterial=jsonObject.getInt("purchaseSampleMaterial");
            taskNumber.sendClothes=jsonObject.getInt("sendClothes");
            taskNumber.confirmCad=jsonObject.getInt("confirmCad");
            taskNumber.verifyQuote=jsonObject.getInt("verifyQuote");
            taskNumber.receiveSample=jsonObject.getInt("receiveSample");
            taskNumber.computePurchaseCost=jsonObject.getInt("computePurchaseCost");
            taskNumber.confirmDeposit=jsonObject.getInt("confirmDeposit");
            taskNumber.craftSample=jsonObject.getInt("craftSample");
            taskNumber.confirmPurchase=jsonObject.getInt("confirmPurchase");
            taskNumber.checkQuality=jsonObject.getInt("checkQuality");
            taskNumber.verifyAlter=jsonObject.getInt("verifyAlter");
            taskNumber.warehouse=jsonObject.getInt("warehouse");
            taskNumber.produce=jsonObject.getInt("produce");
            taskNumber.confirmFinalPayment=jsonObject.getInt("confirmFinalPayment");
            taskNumber.uploadDegisn=jsonObject.getInt("uploadDegisn");
            taskNumber.verifyPurchase=jsonObject.getInt("verifyPurchase");
            taskNumber.buySweaterMaterial=jsonObject.getInt("buySweaterMaterial");
            taskNumber.computeProduceCost=jsonObject.getInt("computeProduceCost");
            taskNumber.returnDeposit=jsonObject.getInt("returnDeposit");
            taskNumber.warehouse_haoduoyi=jsonObject.getInt("warehouse_haoduoyi");
            taskNumber.confirmDesign=jsonObject.getInt("confirmDesign");
            taskNumber.confirmSampleMoney=jsonObject.getInt("confirmSampleMoney");
            taskNumber.allocateOrder=jsonObject.getInt("allocateOrder");
            taskNumber.modifyDesign=jsonObject.getInt("modifyDesign");
            taskNumber.sendSweater=jsonObject.getInt("sendSweater");
            taskNumber.marketManager2=jsonObject.getInt("marketManager2");
            taskNumber.craftManager=jsonObject.getInt("craftManager");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskNumber;
    }

    public int[] getDepartmentTaskNumbers(){
        int [] departmentNumbers = {
                marketManager2,
                marketManager,
                marketSecretary,
                designManager,
                craftManager,
                purchaseManager,
                produceManager,
                SweaterMakeManager,
                financeManager,
                logisticsManager,
                qualityManager,
                0,
                0
        };
        //最后两个0表示的是人事部和系统管理的值
        return departmentNumbers;
    }
}
