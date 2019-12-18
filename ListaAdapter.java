package app.projectwork.simulazione;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import app.projectwork.simulazione.Entita.Libro;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Libro> mItem;
    private final int mid;

    public ListaAdapter(Context context, List<Libro> booklist, int p) {
        this.mContext = context;
        this.mItem = booklist;
        this.mid = p;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, data1, data2;
        final CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.element_Name);
            data1 = (TextView) itemView.findViewById(R.id.element_data1);
            data2 = (TextView) itemView.findViewById(R.id.element_data2);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_element, viewGroup, false);
        ListaAdapter.ViewHolder viewHolder = new ListaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListaAdapter.ViewHolder holder, final int position) {
        final Libro item = mItem.get(position);
        holder.name.setText(item.getTitolo());
        holder.data1.setText(item.getAutore());
        holder.data2.setText(item.getCasaEditrice());
        holder.checkBox.setChecked(item.isRead());

        //qualora lo stato della checkbox cambiasse per la pressione dell'utente
        //controllo in quale stato si trova se attivo o disattivato
        //e aggiorna la lista collegata all'utente
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    //se era disattivato e ora è attivato setto il libro come letto
                    Libro l = new Libro(position, item.getTitolo(), item.getAutore(), item.getCasaEditrice(), true);
                    DatiMock.getInstance().aggiornaMappa(mid, position, l);

                } else {
                    //se era attivo e ora è stato disattivato setto il libro come letto
                    Libro l = new Libro(position, item.getTitolo(), item.getAutore(), item.getCasaEditrice(), false);
                    DatiMock.getInstance().aggiornaMappa(mid, position, l);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
}
