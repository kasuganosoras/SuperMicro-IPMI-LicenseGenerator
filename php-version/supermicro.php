<?php
class Supermicro {
	
	private function getBin($hexdata)
	{
		$bindata = "";
		for ($i = 0;$i < strlen($hexdata);$i += 2) {
			$bindata .= chr(hexdec(substr($hexdata, $i, 2)));
		}
		return $bindata;
	}
	
	private function getKey($dmac)
	{
		return substr(hash_hmac("sha1", $this->getBin($dmac), hex2bin('8544E3B47ECA58F9583043F8')), 0, 24);
	}
	
	public function getLicense($mac, $source = false)
	{
		$mac = str_replace(":", "", $mac);
		if(preg_match("/^[a-z0-9]{12}$/", $mac)) {
			$key = $this->getKey($mac);
			if(empty($key)) {
				return false;
			}
			if($source) {
				return $key;
			}
			$s = 0;
			$result = "";
			for($i = 0;$i < strlen($key);$i++) {
				$s++;
				$result .= $key[$i];
				if($s == 4 && $i !== strlen($key) - 1) {
					$result .= "-";
					$s = 0;
				}
			}
			return $result;
		}
		return false;
	}
}
