package so.codeweaver.neigh;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;

/**
 * Created by berwyn on 07/10/14.
 */
public class Source implements Parcelable {

    public Uri     dataUri;
    public Uri     imageUri;
    public String  name;
    public boolean isStream;
    @ColorRes
    public int     notificationColor;
    public Intent  contentIntent;

    public Source() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dataUri, 0);
        dest.writeParcelable(this.imageUri, 0);
        dest.writeString(this.name);
        dest.writeByte(isStream ? (byte) 1 : (byte) 0);
        dest.writeInt(this.notificationColor);
        dest.writeParcelable(this.contentIntent, 0);
    }

    private Source(Parcel in) {
        this.dataUri = in.readParcelable(Uri.class.getClassLoader());
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());
        this.name = in.readString();
        this.isStream = in.readByte() != 0;
        this.notificationColor = in.readInt();
        this.contentIntent = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}
