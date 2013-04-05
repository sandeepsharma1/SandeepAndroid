package com.netcomps.oauth_example;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements Filterable {
	Context context;
	ArrayList<MailContacts> mList;
	ArrayList<MailContacts> secondList;
	SparseBooleanArray mSparseBooleanArray;

	private Filter arrangeFilter;

	public MyAdapter(Context applicationContext, ArrayList<MailContacts> mList) {
		// TODO Auto-generated constructor stub
		context = applicationContext;
		this.mList = mList;
		secondList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		LayoutInflater mInflater;
		if (convertView == null) {
			mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.inflate, null);
			holder = new ViewHolder();
			holder.eMail = (TextView) convertView.findViewById(R.id.gmail);
			holder.contactName = (TextView) convertView
					.findViewById(R.id.contactname);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.eMail.setText(mList.get(position).getContactEmail());
		holder.contactName.setText(mList.get(position).getContactName());
		return convertView;

	}

	public void resetData() {
		mList = secondList;
	}

	class ViewHolder {

		TextView eMail;
		TextView contactName;
	}

	@Override
	public Filter getFilter() {
		if (arrangeFilter == null)
			arrangeFilter = new ArrangeFilter();

		return arrangeFilter;
	}

	private class ArrangeFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = secondList;
				results.count = secondList.size();
			} else {
				// We perform filtering operation
				List<MailContacts> nArrangedList = new ArrayList<MailContacts>();

				for (MailContacts p : mList) {
					if (p.getContactEmail().toUpperCase()
							.startsWith(constraint.toString().toUpperCase()))
						nArrangedList.add(p);
				}

				results.values = nArrangedList;
				results.count = nArrangedList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// Now we have to inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				mList = (ArrayList<MailContacts>) results.values;
				notifyDataSetChanged();
			}

		}

	}

	public ArrayList<MailContacts> getCheckedItems() {

		ArrayList<MailContacts> mTempArry = new ArrayList<MailContacts>();

		for (int i = 0; i < mList.size(); i++) {

			if (mSparseBooleanArray.get(i)) {

				mTempArry.add(mList.get(i));

			}

		}
		return mTempArry;
	}

}
