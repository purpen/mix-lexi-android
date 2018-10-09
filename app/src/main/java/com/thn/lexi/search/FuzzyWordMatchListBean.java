package com.thn.lexi.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FuzzyWordMatchListBean implements Parcelable {

    /**
     * data : {"count":3,"next":false,"prev":true,"qk":"商品分享","search_items":[{"matches":[{"match":"false, 该关键词是否匹true为匹配, false则没匹配到","word":"商品"},{"match":true,"word":"分享"}],"name":"商品名","serial_no":"8208961374","target_type":"1=商品, 2=原创品牌设计馆, 3=用户","title":"商品"},{"matches":[{"match":false,"word":"商品"},{"match":true,"word":"分享"}],"name":"品牌馆名","serial_no":"19620734815","target_type":2,"title":"品牌馆"},{"matches":[{"match":false,"word":"商品"},{"match":true,"word":"分享"}],"name":"用户名","serial_no":"19620734815","target_type":3,"title":"用户"}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 3
         * next : false
         * prev : true
         * qk : 商品分享
         * search_items : [{"matches":[{"match":"false, 该关键词是否匹true为匹配, false则没匹配到","word":"商品"},{"match":true,"word":"分享"}],"name":"商品名","serial_no":"8208961374","target_type":"1=商品, 2=原创品牌设计馆, 3=用户","title":"商品"},{"matches":[{"match":false,"word":"商品"},{"match":true,"word":"分享"}],"name":"品牌馆名","serial_no":"19620734815","target_type":2,"title":"品牌馆"},{"matches":[{"match":false,"word":"商品"},{"match":true,"word":"分享"}],"name":"用户名","serial_no":"19620734815","target_type":3,"title":"用户"}]
         */

        public int count;
        public String qk;
        public List<SearchItemsBean> search_items;

        public static class SearchItemsBean implements Parcelable {
            /**
             * matches : [{"match":"false, 该关键词是否匹true为匹配, false则没匹配到","word":"商品"},{"match":true,"word":"分享"}]
             * name : 商品名
             * serial_no : 8208961374
             * target_type : 1=商品, 2=原创品牌设计馆, 3=用户
             * title : 商品
             */

            public String name;
            public String serial_no;
            public int target_type;
            public String title;
            public List<MatchesBean> matches;

            public static class MatchesBean implements Parcelable {
                /**
                 * match : false, 该关键词是否匹true为匹配, false则没匹配到
                 * word : 商品
                 */

                public boolean match;
                public String word;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeByte(this.match ? (byte) 1 : (byte) 0);
                    dest.writeString(this.word);
                }

                public MatchesBean() {
                }

                protected MatchesBean(Parcel in) {
                    this.match = in.readByte() != 0;
                    this.word = in.readString();
                }

                public static final Creator<MatchesBean> CREATOR = new Creator<MatchesBean>() {
                    @Override
                    public MatchesBean createFromParcel(Parcel source) {
                        return new MatchesBean(source);
                    }

                    @Override
                    public MatchesBean[] newArray(int size) {
                        return new MatchesBean[size];
                    }
                };
            }

            public SearchItemsBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeString(this.serial_no);
                dest.writeInt(this.target_type);
                dest.writeString(this.title);
                dest.writeList(this.matches);
            }

            protected SearchItemsBean(Parcel in) {
                this.name = in.readString();
                this.serial_no = in.readString();
                this.target_type = in.readInt();
                this.title = in.readString();
                this.matches = new ArrayList<MatchesBean>();
                in.readList(this.matches, MatchesBean.class.getClassLoader());
            }

            public static final Creator<SearchItemsBean> CREATOR = new Creator<SearchItemsBean>() {
                @Override
                public SearchItemsBean createFromParcel(Parcel source) {
                    return new SearchItemsBean(source);
                }

                @Override
                public SearchItemsBean[] newArray(int size) {
                    return new SearchItemsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeString(this.qk);
            dest.writeList(this.search_items);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.qk = in.readString();
            this.search_items = new ArrayList<SearchItemsBean>();
            in.readList(this.search_items, SearchItemsBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public static class StatusBean implements Parcelable {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.message);
        }

        public StatusBean() {
        }

        protected StatusBean(Parcel in) {
            this.code = in.readInt();
            this.message = in.readString();
        }

        public static final Creator<StatusBean> CREATOR = new Creator<StatusBean>() {
            @Override
            public StatusBean createFromParcel(Parcel source) {
                return new StatusBean(source);
            }

            @Override
            public StatusBean[] newArray(int size) {
                return new StatusBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public FuzzyWordMatchListBean() {
    }

    protected FuzzyWordMatchListBean(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FuzzyWordMatchListBean> CREATOR = new Parcelable.Creator<FuzzyWordMatchListBean>() {
        @Override
        public FuzzyWordMatchListBean createFromParcel(Parcel source) {
            return new FuzzyWordMatchListBean(source);
        }

        @Override
        public FuzzyWordMatchListBean[] newArray(int size) {
            return new FuzzyWordMatchListBean[size];
        }
    };
}
