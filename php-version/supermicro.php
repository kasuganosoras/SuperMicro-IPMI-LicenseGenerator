<?php
class Supermicro {

    const SUPERMICRO_OOB_PRIVATE_KEY_HEX = '8544E3B47ECA58F9583043F8';

    /**
     * @param string $BMC_MAC
     * @return string
     */
    public function getLicense(string $BMC_MAC, bool $source = false)
    {
        $key = $this->getKey($BMC_MAC);
        return $source ? $key : implode(
            ' - ',
            array_slice(
                str_split(
                    $key, 4
                ), 0, 6
            )
        );
    }

    protected function getKey(string $BMC_MAC)
    {
        return hash_hmac(
            'sha1',
            hex2bin(
                preg_replace(
                    '![^A-F0-9]!i', '', $BMC_MAC
                )
            ), hex2bin(self::SUPERMICRO_OOB_PRIVATE_KEY_HEX)
        );
    }
}
