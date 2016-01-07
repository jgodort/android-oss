package com.kickstarter.services;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kickstarter.R;
import com.kickstarter.libs.qualifiers.AutoGson;
import com.kickstarter.libs.utils.ObjectUtils;
import com.kickstarter.models.Category;
import com.kickstarter.models.Location;
import com.kickstarter.models.Project;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import auto.parcel.AutoParcel;

@AutoGson
@AutoParcel
public abstract class DiscoveryParams implements Parcelable {
  @Nullable public abstract Integer backed();
  @Nullable public abstract Category category();
  @Nullable public abstract String categoryParam();
  @Nullable public abstract Location location();
  @Nullable public abstract String locationParam();
  @Nullable public abstract Integer page();
  @Nullable public abstract Integer perPage();
  @Nullable public abstract Boolean staffPicks();
  @Nullable public abstract Integer starred();
  @Nullable public abstract Integer social();
  @Nullable public abstract Sort sort();
  @Nullable public abstract Boolean recommended();
  @Nullable public abstract Project similarTo();
  @Nullable public abstract String term();

  public enum Sort {
    MAGIC, POPULAR, ENDING_SOON, NEWEST, MOST_FUNDED;
    @Override
    public @NonNull String toString() throws AssertionError {
      switch (this) {
        case MAGIC:
          return "magic";
        case POPULAR:
          return "popularity";
        case ENDING_SOON:
          return "end_date";
        case NEWEST:
          return "newest";
        case MOST_FUNDED:
          return "most_funded";
      }
      throw new AssertionError("Unhandled sort");
    }

    public static @NonNull Sort fromString(final @NonNull String string) throws AssertionError {
      switch (string) {
        case "magic":
          return MAGIC;
        case "popularity":
          return POPULAR;
        case "end_date":
          return ENDING_SOON;
        case "newest":
          return NEWEST;
        case "most_funded":
          return MOST_FUNDED;
      }
      throw new AssertionError("Unhandled sort");
    }
  }

  public static @NonNull DiscoveryParams fromUri(final @NonNull Uri uri, final @NonNull Builder initialBuilder) {
    Builder builder = initialBuilder;

    final Integer backed = ObjectUtils.toInteger(uri.getQueryParameter("backed"));
    if (backed != null) {
      builder = builder.backed(backed);
    }

    if (KSUri.isDiscoverCategoriesPath(uri.getPath())) {
      builder = builder.categoryParam(uri.getLastPathSegment());
    }

    final String categoryParam = uri.getQueryParameter("category_id");
    if (categoryParam != null) {
      builder = builder.categoryParam(categoryParam);
    }

    if (KSUri.isDiscoverPlacesPath(uri.getPath())) {
      builder = builder.locationParam(uri.getLastPathSegment());
    }

    final String locationParam = uri.getQueryParameter("location_id");
    if (locationParam != null) {
      builder = builder.locationParam(locationParam);
    }

    final Integer page = ObjectUtils.toInteger(uri.getQueryParameter("page"));
    if (page != null) {
      builder = builder.page(page);
    }

    final Integer perPage = ObjectUtils.toInteger(uri.getQueryParameter("per_page"));
    if (perPage != null) {
      builder = builder.perPage(perPage);
    }

    final Boolean recommended = ObjectUtils.toBoolean(uri.getQueryParameter("recommended"));
    if (recommended != null) {
      builder = builder.recommended(recommended);
    }

    final Integer social = ObjectUtils.toInteger(uri.getQueryParameter("social"));
    if (social != null) {
      builder = builder.social(social);
    }

    final Boolean staffPicks = ObjectUtils.toBoolean(uri.getQueryParameter("staff_picks"));
    if (staffPicks != null) {
      builder = builder.staffPicks(staffPicks);
    }

    final String sortString = uri.getQueryParameter("sort");
    if (sortString != null) {
      try {
        final Sort sort = Sort.fromString(uri.getQueryParameter("sort"));
        if (sort != null) {
          builder = builder.sort(sort);
        }
      } catch (final AssertionError e) {}
    }

    final Integer starred = ObjectUtils.toInteger(uri.getQueryParameter("starred"));
    if (starred != null) {
      builder = builder.starred(starred);
    }

    final String term = uri.getQueryParameter("term");
    if (term != null) {
      builder = builder.term(term);
    }

    return builder.build();
  }

  public static @NonNull DiscoveryParams fromUri(final @NonNull Uri uri) {
    return fromUri(uri, DiscoveryParams.builder());
  }

  @AutoParcel.Builder
  public abstract static class Builder {
    public abstract Builder backed(Integer __);
    public abstract Builder category(Category __);
    public abstract Builder categoryParam(String __);
    public abstract Builder location(Location __);
    public abstract Builder locationParam(String __);
    public abstract Builder page(Integer __);
    public abstract Builder perPage(Integer __);
    public abstract Builder sort(Sort __);
    public abstract Builder staffPicks(Boolean __);
    public abstract Builder starred(Integer __);
    public abstract Builder social(Integer __);
    public abstract Builder recommended(Boolean __);
    public abstract Builder similarTo(Project __);
    public abstract Builder term(String __);
    public abstract DiscoveryParams build();

    public @NonNull DiscoveryParams.Builder mergeWith(final @NonNull Builder otherBuilder) {
      final DiscoveryParams other = otherBuilder.build();
      DiscoveryParams.Builder retVal = this;

      if (other.backed() != null) {
        retVal = retVal.backed(other.backed());
      }
      if (other.category() != null) {
        retVal = retVal.category(other.category());
      }
      if (other.categoryParam() != null) {
        retVal = retVal.categoryParam(other.categoryParam());
      }
      if (other.location() != null) {
        retVal = retVal.location(other.location());
      }
      if (other.page() != null) {
        retVal = retVal.page(other.page());
      }
      if (other.perPage() != null) {
        retVal = retVal.perPage(other.perPage());
      }
      if (other.social() != null) {
        retVal = retVal.social(other.social());
      }
      if (other.staffPicks() != null) {
        retVal = retVal.staffPicks(other.staffPicks());
      }
      if (other.starred() != null) {
        retVal = retVal.starred(other.starred());
      }
      if (other.sort() != null) {
        retVal = retVal.sort(other.sort());
      }
      if (other.recommended() != null) {
        retVal = retVal.recommended(other.recommended());
      }
      if (other.similarTo() != null) {
        retVal = retVal.similarTo(other.similarTo());
      }
      if (other.term() != null) {
        retVal = retVal.term(other.term());
      }

      return retVal;
    }
  }

  public static @NonNull Builder builder() {
    return new AutoParcel_DiscoveryParams.Builder()
      .page(1)
      .perPage(15)
      .sort(Sort.MAGIC);
  }

  public abstract Builder toBuilder();

  public @NonNull DiscoveryParams nextPage () {
    final Integer page = page();
    return page != null ? toBuilder().page(page + 1).build() : this;
  }

  public @NonNull Map<String, String> queryParams() {
    return Collections.unmodifiableMap(new HashMap<String, String>() {{
      if (backed() != null) {
        put("backed", String.valueOf(backed()));
      }

      if (category() != null) {
        put("category_id", String.valueOf(category().id()));
      }

      if (categoryParam() != null) {
        put("category_id", categoryParam());
      }

      if (location() != null) {
        put("woe_id", String.valueOf(location().id()));
      }

      if (locationParam() != null) {
        put("woe_id", locationParam());
      }

      if (page() != null) {
        put("page", String.valueOf(page()));
      }

      if (perPage() != null) {
        put("per_page", String.valueOf(perPage()));
      }

      if (recommended() != null) {
        put("recommended", String.valueOf(recommended()));
      }

      if (similarTo() != null) {
        put("similar_to", String.valueOf(similarTo().id()));
      }

      if (starred() != null) {
        put("starred", String.valueOf(starred()));
      }

      if (social() != null) {
        put("social", String.valueOf(social()));
      }

      if (sort() != null) {
        put("sort", sort().toString());
      }

      if (staffPicks() != null) {
        put("staff_picks", String.valueOf(staffPicks()));
      }

      if (term() != null) {
        put("q", term());
      }

      if (staffPicks() != null && staffPicks() && page() != null && page() == 1) {
        put("include_potd", "true");
      }

      if (category() != null && page() != null && page() == 1) {
        put("include_featured", "true");
      }
    }});
  }

  @Override
  public @NonNull String toString() {
    return queryParams().toString();
  }

  public @NonNull String filterString(@NonNull final Context context) {
    if (staffPicks() != null && staffPicks()) {
      return context.getString(R.string.discovery_recommended);
    } else if (starred() != null && starred() == 1) {
      return context.getString(R.string.discovery_saved);
    } else if (backed() != null && backed() == 1) {
      return context.getString(R.string.discovery_backing);
    } else if (social() != null && social() == 1) {
      return context.getString(R.string.discovery_friends_backed);
    } else if (category() != null) {
      return category().name();
    } else if (location() != null) {
      return location().displayableName();
    } else {
      return context.getString(R.string.discovery_everything);
    }
  }

  public boolean isCategorySet() {
    return category() != null;
  }
}
