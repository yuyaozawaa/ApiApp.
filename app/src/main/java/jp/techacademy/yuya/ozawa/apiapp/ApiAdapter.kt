package jp.techacademy.yuya.ozawa.apiapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.techacademy.yuya.ozawa.apiapp.databinding.RecyclerFavoriteBinding

/**
 * RecyclerView用Adapter
 * 第一引数: データを保持するクラス。今回はShop
 * 第二引数: リスト内の1行の内容を保持するViewHolder。今回はApiItemViewHolder
 */
class ApiAdapter : ListAdapter<Shop, ApiItemViewHolder>(ApiItemCallback()) {

    // 一覧画面から登録するときのコールバック（FavoriteFragmentへ通知するメソッド)
    var onClickAddFavorite: ((Shop) -> Unit)? = null

    // 一覧画面から削除するときのコールバック（ApiFragmentへ通知するメソッド)
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null

    // Itemを押したときのメソッド
    var onClickItem: ((String, String, String, String) -> Unit)? = null


    /**
     * ViewHolderを生成して返す
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiItemViewHolder {
        // ViewBindingを引数にApiItemViewHolderを生成
        val view =
            RecyclerFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApiItemViewHolder(view)
    }

    /**
     * 指定された位置（position）のViewにShopの情報をセットする
     */
    override fun onBindViewHolder(holder: ApiItemViewHolder, position: Int) {
        holder.bind(getItem(position), position, this)
    }
}

/**
 * リスト内の1行の内容を保持する
 */
class ApiItemViewHolder(private val binding: RecyclerFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(shop: Shop, position: Int, adapter: ApiAdapter) {
        binding.rootView.apply {
            // 偶数番目と奇数番目で背景色を変更させる
            binding.rootView.setBackgroundColor(
                ContextCompat.getColor(
                    binding.rootView.context,
                    if (position % 2 == 0) android.R.color.white else android.R.color.darker_gray
                )
            )
            setOnClickListener {
                // onClickItem関数を呼び出す
                adapter.onClickItem?.invoke(shop.couponUrls.sp.ifEmpty { shop.couponUrls.pc }, shop.id, shop.logoImage, shop.name)
            }
        }

        // 1行の項目にShopの値をセット
        // nameTextViewのtextプロパティに代入されたオブジェクトのnameプロパティを代入
        binding.nameTextView.text = shop.name
        binding.addressTextView.text = shop.address


        // Picassoライブラリを使い、imageViewにdata.logoImageのurlの画像を読み込ませる
        Picasso.get().load(shop.logoImage).into(binding.imageView)

        // 星の処理
        binding.favoriteImageView.apply {
            // お気に入り状態を取得
            val isFavorite = FavoriteShop.findBy(shop.id) != null

            // 白抜きの星を設定
            setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)

            // 星をタップした時の処理
            setOnClickListener {
                if (isFavorite) {
                    adapter.onClickDeleteFavorite?.invoke(shop)
                } else {
                    adapter.onClickAddFavorite?.invoke(shop) //この処理でデータを渡す
                }
                adapter.notifyItemChanged(position)
            }
        }
    }
}

/**
 * データの差分を確認するクラス
 */
internal class ApiItemCallback : DiffUtil.ItemCallback<Shop>() {

    override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
        return oldItem == newItem
    }
}